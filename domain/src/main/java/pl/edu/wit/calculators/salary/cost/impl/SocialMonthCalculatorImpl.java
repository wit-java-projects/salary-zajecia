package pl.edu.wit.calculators.salary.cost.impl;

import org.springframework.stereotype.Component;
import pl.edu.wit.calculators.salary.cost.CostCalculator;
import pl.edu.wit.calculators.salary.cost.model.SocialCost;
import pl.edu.wit.calculators.salary.cost.model.configuration.SocialCostConfig;
import pl.edu.wit.calculators.salary.cost.model.parameters.SocialCalculatorParams;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

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
        final BigDecimal disability = BigDecimal.ZERO;
        // 4. oblicz koszt chorobowy
        final BigDecimal sickness = BigDecimal.ZERO;
        // 5. oblicz kwotę bazową zdrowotnego
        final BigDecimal baseMedical = BigDecimal.ZERO;
        // 6. oblicz koszt zdrowotny
        final BigDecimal medical = BigDecimal.ZERO;

        return SocialCost.builder()
                .sickness(sickness)
                .medical(medical)
                .pension(pension)
                .disability(disability)
                .build();
    }

    private BigDecimal calculatePension(final BigDecimal baseSalary, final SocialCostConfig socialConfiguration) {
        return null;
    }

    private BigDecimal calculateBaseSalary(final SocialCalculatorParams params) {
        final BigDecimal baseSumSalary = this.accumulateSalary.accumulateAndGet(params.getSalary(), BigDecimal::add);
        final BigDecimal maxAccumulateSalary = params.getConfiguration().getSocial().getMaxAccumulateSalary();

        final BigDecimal deduction = baseSumSalary.subtract(maxAccumulateSalary).max(BigDecimal.ZERO);

        return params.getSalary().subtract(deduction).max(BigDecimal.ZERO);
    }

}
