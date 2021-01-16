package pl.edu.wit.calculators.salary.cost.impl;

import org.springframework.stereotype.Component;
import pl.edu.wit.calculators.salary.cost.CostCalculator;
import pl.edu.wit.calculators.salary.cost.model.SocialCost;
import pl.edu.wit.calculators.salary.cost.model.TaxCost;
import pl.edu.wit.calculators.salary.cost.model.configuration.TaxCostConfig;
import pl.edu.wit.calculators.salary.cost.model.parameters.TaxCalculatorParams;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class TaxMonthCalculatorImpl extends CostCalculator<TaxCost, TaxCalculatorParams> {

    /**
     * accumulate salary for calculations.
     */
    private final AtomicReference<BigDecimal> accumulateSalary = new AtomicReference<>(BigDecimal.ZERO);
    /**
     * accumulate tax for calculations.
     */
    private final AtomicReference<BigDecimal> accumulateTax = new AtomicReference<>(BigDecimal.ZERO);

    @Override
    public int getYear() {
        return 2020;
    }

    @Override
    protected TaxCost calculateInternal(TaxCalculatorParams params) {

        final SocialCost costs = params.getSocialCost();
        // 1. wyliczać bazową kwote
        final BigDecimal baseSalary = calculateBaseSalary(params, costs.getPension(), costs.getDisability(), costs.getSickness());
        final BigDecimal accumulateSalaryBase = this.accumulateSalary.accumulateAndGet(baseSalary, BigDecimal::add);

        // 2. wyliczać podatek
        final BigDecimal taxAdvance = calculateAdvance(params, baseSalary, accumulateSalaryBase);

        return TaxCost.builder()
                .baseTaxSalary(baseSalary)
                .taxAdvanced(taxAdvance)
                .build();
    }


    private BigDecimal calculateBaseSalary(final TaxCalculatorParams params, final BigDecimal... deductions) {
        return params.getSalary().subtract(Arrays.stream(deductions).reduce(BigDecimal::add).orElse(BigDecimal.ZERO))
                .subtract(params.getConfiguration().getTax().getTaxDeductibleCost());
    }

    protected BigDecimal calculateAdvance(final TaxCalculatorParams params, final BigDecimal baseSalary, final BigDecimal accumulateSalaryBase) {
        final TaxCostConfig taxCostConfig = params.getConfiguration().getTax();
        final BigDecimal tax = calculateTax(taxCostConfig, accumulateSalaryBase);

        accumulateTax.accumulateAndGet(tax, BigDecimal::add);

        return formatValue(tax
                .subtract(calculateTaxMedicalReduction(taxCostConfig, baseSalary))
                .subtract(calculateTaxReduction(taxCostConfig, accumulateSalaryBase))
                .max(BigDecimal.ZERO));
    }

    @Override
    protected BigDecimal formatValue(final BigDecimal base) {
        return super.formatValue(base.setScale(0, RoundingMode.HALF_DOWN));
    }

    private BigDecimal calculateTax(final TaxCostConfig config, final BigDecimal accumulateSalaryBase) {
        return config.getThresholds().stream()
                .filter(filterThresholds(accumulateSalaryBase))
                .map(calculateThresholdTax(accumulateSalaryBase))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .subtract(accumulateTax.get());
    }

    private BigDecimal calculateTaxMedicalReduction(final TaxCostConfig config, final BigDecimal baseSalary) {
        return calculateValue(baseSalary.add(config.getTaxDeductibleCost()), config.getMedical());
    }

    private BigDecimal calculateTaxReduction(final TaxCostConfig config, final BigDecimal salaryBase) {
        return config.getThresholds().stream()
                .filter(filterThresholds(salaryBase))
                .count() == 1 ? BigDecimal.valueOf(43.76) : BigDecimal.ZERO;
    }

    private Predicate<TaxCostConfig.TaxThreshold> filterThresholds(final BigDecimal salaryBase) {
        return (tax) -> salaryBase.compareTo(tax.getMinSalary()) >= 0;
    }

    private Function<TaxCostConfig.TaxThreshold, BigDecimal> calculateThresholdTax(final BigDecimal salaryBase) {

        return tax -> {
            final BigDecimal maxSalary = tax.getMaxSalary().orElse(BigDecimal.ZERO);
            if (salaryBase.compareTo(maxSalary) > 0) {
                final BigDecimal base = tax.getMaxSalary().orElse(salaryBase);
                return calculateValue(base, tax.getPercent());
            }
            return calculateValue(salaryBase.subtract((tax.getMinSalary())), tax.getPercent());
        };
    }
}
