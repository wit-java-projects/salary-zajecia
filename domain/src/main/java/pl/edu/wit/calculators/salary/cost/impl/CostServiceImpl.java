package pl.edu.wit.calculators.salary.cost.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wit.calculators.salary.cost.CostCalculator;
import pl.edu.wit.calculators.salary.cost.CostService;
import pl.edu.wit.calculators.salary.cost.model.MonthCost;
import pl.edu.wit.calculators.salary.cost.model.SocialCost;
import pl.edu.wit.calculators.salary.cost.model.TaxCost;
import pl.edu.wit.calculators.salary.cost.model.parameters.CostParams;
import pl.edu.wit.calculators.salary.cost.model.parameters.SocialCalculatorParams;
import pl.edu.wit.calculators.salary.cost.model.parameters.TaxCalculatorParams;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {

    private final List<CostCalculator<SocialCost, SocialCalculatorParams>> socialCostCalculators;
    private final List<CostCalculator<TaxCost, TaxCalculatorParams>> taxCostCalculators;

    @Override
    public List<MonthCost> calculate(final CostParams params) {

        // 1. wybierz kalkulator kosztow socialnych
        // 2. wybierz kalkulator kosztow podatkowych

        // pentla dla kazdego miesiaca
        // 3. wykonamy metode calculate -> SocialCost
        // 4. wykonamy metode calculate -> TaxCost

        // 5. polaczymy koszyty w MonthCost
        // koniec pentli.

        return null;
    }
}
