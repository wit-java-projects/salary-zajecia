package pl.edu.wit.calculators.salary.cost.impl;

import org.springframework.stereotype.Component;
import pl.edu.wit.calculators.salary.cost.CostCalculator;
import pl.edu.wit.calculators.salary.cost.model.SocialCost;
import pl.edu.wit.calculators.salary.cost.model.configuration.SocialCostConfig;
import pl.edu.wit.calculators.salary.cost.model.parameters.SocialCalculatorParams;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

@Component
public class SocialMonthCalculatorImpl extends CostCalculator<SocialCost, SocialCalculatorParams> {

    private AtomicReference<BigDecimal> accumulateSalary;

    public SocialMonthCalculatorImpl() {
        this.accumulateSalary = new AtomicReference<>();
    }

    @Override
    public int getYear() {
        return 2020;
    }

    @Override
    protected SocialCost calculateInternal(final SocialCalculatorParams params) {

        final SocialCostConfig socialConfiguration = params.getConfiguration().getSocial();

        // 1. oblicz kwote bazową
        final BigDecimal baseSalary = calculateBaseSalary(params);
        // 2. oblicz koszt emerytalny
        final BigDecimal pension = calculatePension(baseSalary, socialConfiguration);
        // 3. oblicz koszt rentowy
        final BigDecimal disability = calculateDisability(baseSalary, socialConfiguration);
        // 4. oblicz koszt chorobowy
        final BigDecimal sickness = calculateSickness(params.getSalary(), socialConfiguration);
        // 5. oblicz kwotę bazową zdrowotnego
        final BigDecimal baseMedical = calculateBaseMedical(params.getSalary(), pension, disability, sickness);
        // 6. oblicz koszt zdrowotny
        final BigDecimal medical = calculateMedical(baseMedical, socialConfiguration);

        return SocialCost.builder()
                .sickness(sickness)
                .medical(medical)
                .pension(pension)
                .disability(disability)
                .build();
    }

    private BigDecimal calculateMedical(BigDecimal baseMedical, SocialCostConfig socialConfiguration) {
        return calculateValue(baseMedical, socialConfiguration.getMedical());
    }

    protected BigDecimal calculateBaseMedical(BigDecimal salary, BigDecimal... deduction) { // varargs
//        BigDecimal sum = BigDecimal.ZERO;
//        for ( BigDecimal value : deduction ) {
//            sum = sum.add(value);
//        }

        BigDecimal sum = Arrays.stream(deduction)
                .reduce(BigDecimal::add) // sum2.add(value)
                .orElse(BigDecimal.ZERO);

        return salary.subtract(sum);
    }

    protected BigDecimal calculateSickness(BigDecimal salary, SocialCostConfig socialConfiguration) {
        return calculateValue(salary, socialConfiguration.getSickness());
    }

    protected BigDecimal calculateDisability(BigDecimal baseSalary, SocialCostConfig socialConfiguration) {
        return calculateValue(baseSalary, socialConfiguration.getDisability());
    }

    protected BigDecimal calculatePension(final BigDecimal baseSalary, final SocialCostConfig socialConfiguration) {
        return calculateValue(baseSalary, socialConfiguration.getPension());
    }

    protected BigDecimal calculateBaseSalary(final SocialCalculatorParams params) {
        final BigDecimal baseSumSalary = this.accumulateSalary.accumulateAndGet(params.getSalary(), BigDecimal::add);
        final BigDecimal maxAccumulateSalary = params.getConfiguration().getSocial().getMaxAccumulateSalary();

        final BigDecimal deduction = baseSumSalary.subtract(maxAccumulateSalary).max(BigDecimal.ZERO);

        return params.getSalary().subtract(deduction).max(BigDecimal.ZERO);
    }

}
