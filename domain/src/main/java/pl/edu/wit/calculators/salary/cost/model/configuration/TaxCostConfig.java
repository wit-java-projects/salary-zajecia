package pl.edu.wit.calculators.salary.cost.model.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class TaxCostConfig {

    private final double medical;

    private final BigDecimal taxDeductibleCost;

    @Singular
    private final List<TaxThreshold> thresholds;

    @RequiredArgsConstructor
    public static class TaxThreshold {
        private final BigDecimal minSalary;
        private final BigDecimal maxSalary;
        private final double percent;
    }


}
