package pl.edu.wit.calculators.salary.cost.model.parameters;

import lombok.Builder;
import lombok.Getter;
import pl.edu.wit.calculators.salary.cost.CalculatorParams;
import pl.edu.wit.calculators.salary.cost.model.configuration.CostConfig;

import java.math.BigDecimal;

@Getter
@Builder
public class SocialCalculatorParams implements CalculatorParams {

    private final int year;
    private final BigDecimal salary;

    private final CostConfig configuration;
}
