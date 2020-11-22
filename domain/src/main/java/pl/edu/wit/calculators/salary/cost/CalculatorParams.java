package pl.edu.wit.calculators.salary.cost;

import pl.edu.wit.calculators.salary.cost.model.configuration.CostConfig;

public interface CalculatorParams {

    int getYear();

    CostConfig getConfiguration();
}
