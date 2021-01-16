package pl.edu.wit.calculators.salary.cost.model.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class TaxCostConfig {

    private final double medical;

    private final BigDecimal taxDeductibleCost;

    @Singular
    private final List<TaxThreshold> thresholds;

    @Getter
    @RequiredArgsConstructor
    public static class TaxThreshold {
        private final BigDecimal minSalary;
        private final BigDecimal maxSalary;
        private final double percent;

        public Optional<BigDecimal> getMaxSalary() {
            return Optional.ofNullable(maxSalary);
        }
    }


}
