package pl.edu.wit.calculators.salary.cost.mock;

import org.springframework.stereotype.Repository;
import pl.edu.wit.calculators.salary.cost.model.configuration.CostConfig;
import pl.edu.wit.calculators.salary.cost.model.configuration.SocialCostConfig;
import pl.edu.wit.calculators.salary.cost.model.configuration.TaxCostConfig;

import java.math.BigDecimal;

public class MockCostConfigRepositoryImpl {

    public CostConfig getConfiguration(final int year) {
        return CostConfig.builder()
                .social(createSocialConfig())
                .tax(createTaxConfig())
                .build();
    }

    private TaxCostConfig createTaxConfig() {
        return TaxCostConfig.builder()
                .medical(7.75)
                .threshold(new TaxCostConfig.TaxThreshold(BigDecimal.ZERO, new BigDecimal(85000), 17))
                .threshold(new TaxCostConfig.TaxThreshold(new BigDecimal(85000), null, 32))
                .taxDeductibleCost(new BigDecimal(350))
                .build();
    }

    private SocialCostConfig createSocialConfig() {
        return SocialCostConfig.builder()
                .medical(9)
                .disability(7)
                .maxAccumulateSalary(new BigDecimal(152000))
                .sickness(1.5)
                .pension(7.5)
                .build();
    }
}
