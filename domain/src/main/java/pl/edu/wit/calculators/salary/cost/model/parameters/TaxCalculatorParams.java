package pl.edu.wit.calculators.salary.cost.model.parameters;

import lombok.Builder;
import lombok.Getter;
import pl.edu.wit.calculators.salary.cost.CalculatorParams;
import pl.edu.wit.calculators.salary.cost.model.SocialCost;
import pl.edu.wit.calculators.salary.cost.model.configuration.CostConfig;

import java.math.BigDecimal;

@Getter
@Builder
public class TaxCalculatorParams implements CalculatorParams {
    private final int year;
    private final BigDecimal salary;
    private final boolean over26Years;

    private final SocialCost socialCost;

    private final CostConfig configuration;
}
