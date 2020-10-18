package pl.edu.wit.calculators.salary.cost.model.configuration;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CostConfig {
    private final SocialCostConfig social;
    private final TaxCostConfig tax;
}
