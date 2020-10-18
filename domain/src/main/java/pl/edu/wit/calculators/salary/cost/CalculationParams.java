package pl.edu.wit.calculators.salary.cost;

import pl.edu.wit.calculators.salary.cost.model.configuration.CostConfig;

public interface CalculationParams {

    int getYear();

    CostConfig getConfiguration();
}
