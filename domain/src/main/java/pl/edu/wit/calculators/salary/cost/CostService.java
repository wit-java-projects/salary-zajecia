package pl.edu.wit.calculators.salary.cost;

import pl.edu.wit.calculators.salary.cost.model.MonthCost;
import pl.edu.wit.calculators.salary.cost.model.parameters.CostParams;

import java.util.List;

public interface CostService {
    List<MonthCost> calculate(CostParams params);
}
