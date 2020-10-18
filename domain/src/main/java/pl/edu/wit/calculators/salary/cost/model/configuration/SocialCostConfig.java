package pl.edu.wit.calculators.salary.cost.model.configuration;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialCostConfig {
    private final BigDecimal maxAccumulateSalary;
    private final double pension;
    private final double disability;
    private final double sickness;
    private final double medical;
}
