package pl.edu.wit.calculators.salary.cost.impl;

import pl.edu.wit.calculators.salary.cost.CostCalculator;
import pl.edu.wit.calculators.salary.cost.model.SocialCost;
import pl.edu.wit.calculators.salary.cost.model.TaxCost;
import pl.edu.wit.calculators.salary.cost.model.configuration.CostConfig;
import pl.edu.wit.calculators.salary.cost.model.parameters.TaxCalculatorParams;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

public class TaxMonthCalculatorImpl extends CostCalculator<TaxCost, TaxCalculatorParams> {

    private AtomicReference<BigDecimal> accumulateBase;

    TaxMonthCalculatorImpl() {
        this.accumulateBase = new AtomicReference<>(BigDecimal.ZERO);
    }

    @Override
    public int getYear() {
        return 2020;
    }

    @Override
    protected TaxCost calculateInternal(TaxCalculatorParams params) {

        BigDecimal baseTaxSalary = calculateBaseTaxSalary(params);
        BigDecimal sumBaseTaxSalary = this.accumulateBase.accumulateAndGet(baseTaxSalary, BigDecimal::add);
        BigDecimal taxAdvanced = calculateTaxAdvanced(baseTaxSalary, sumBaseTaxSalary, params.getConfiguration());

        return TaxCost.builder()
                .baseTaxSalary(baseTaxSalary)
                .taxAdvanced(taxAdvanced)
                .build();
    }

    private BigDecimal calculateTaxAdvanced(BigDecimal baseTaxSalary, BigDecimal sumBaseTaxSalary, CostConfig configuration) {
        return null;
    }

    // od kwoty bazowej ( przychód ) odniąc koszty ubezpieczenia ( bez zdrowotnego ) oraz kosz uzyskania przychodu
    private BigDecimal calculateBaseTaxSalary(TaxCalculatorParams params) {

        final BigDecimal salary = params.getSalary();
        final SocialCost socialCost = params.getSocialCost();
        final BigDecimal taxDeductibleCost = params.getConfiguration().getTax().getTaxDeductibleCost();

        return salary.subtract(socialCost.getDeductibleSocialCost())
                .subtract(taxDeductibleCost);


    }
}
