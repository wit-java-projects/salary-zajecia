package pl.edu.wit.calculators.salary.cost.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wit.calculators.salary.cost.CostCalculator;
import pl.edu.wit.calculators.salary.cost.CostService;
import pl.edu.wit.calculators.salary.cost.model.MonthCost;
import pl.edu.wit.calculators.salary.cost.model.SocialCost;
import pl.edu.wit.calculators.salary.cost.model.TaxCost;
import pl.edu.wit.calculators.salary.cost.model.configuration.CostConfig;
import pl.edu.wit.calculators.salary.cost.model.parameters.CostParams;
import pl.edu.wit.calculators.salary.cost.model.parameters.SocialCalculatorParams;
import pl.edu.wit.calculators.salary.cost.model.parameters.TaxCalculatorParams;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {

    private final List<CostCalculator<SocialCost, SocialCalculatorParams>> socialCostCalculators;
    private final List<CostCalculator<TaxCost, TaxCalculatorParams>> taxCostCalculators;

    @Override
    public List<MonthCost> calculate(final CostParams params) {

        // FIXME: pobierz konfiguracje.
        final CostConfig configuration = null;
        // 1. wybierz kalkulator kosztow socialnych
        final CostCalculator<SocialCost, SocialCalculatorParams> socialCalculator = getSocialCalculator(params.getYear());
        // 2. wybierz kalkulator kosztow podatkowych
        final CostCalculator<TaxCost, TaxCalculatorParams> taxCalculator = getTaxCalculator(params.getYear());

        // pentla dla kazdego miesiaca
        // 3. wykonamy metode calculate -> SocialCost
        // 4. wykonamy metode calculate -> TaxCost

        // 5. polaczymy koszyty w MonthCost
        // koniec pentli.


        // pentla dla kazdego miesiaca
        return params.getSalary().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(salary -> {

                    // 3. wykonamy metode calculate -> SocialCost
                    final SocialCost socialCost = calculateSocialSecurityCost(params, socialCalculator, salary, configuration);

                    // 4. wykonamy metode calculate -> TaxCost
                    final TaxCost taxCost = calculateTaxCost(params, taxCalculator, salary, socialCost, configuration);

                    // 5. polaczymy koszyty w MonthCost
                    return MonthCost.builder()
                            .year(params.getYear())
                            .taxCost(taxCost)
                            .socialCost(socialCost)
                            .month(salary.getKey())
                            .grossSalary(salary.getValue())
                            .netSalary(calculateNetSalary(salary.getValue(), socialCost, taxCost))
                            .build();

                })
                // koniec pentli.
                .collect(Collectors.toList());

    }


    private BigDecimal calculateNetSalary(final BigDecimal grossSalary, final SocialCost socialCost, final TaxCost taxCost) {
        return grossSalary.subtract(socialCost.getTotal()).subtract(taxCost.getTaxAdvanced());
    }

    private TaxCost calculateTaxCost(final CostParams params,
                                     final CostCalculator<TaxCost, TaxCalculatorParams> taxCalculator,
                                     final Map.Entry<Month, BigDecimal> salary,
                                     final SocialCost socialCost,
                                     final CostConfig configuration) {

        return taxCalculator.calculate(
                TaxCalculatorParams.builder()
                        .year(params.getYear())
                        .socialCost(socialCost)
                        .salary(salary.getValue())
                        .over26Years(params.isOver26Years())
                        .configuration(configuration)
                        .build());
    }

    private SocialCost calculateSocialSecurityCost(final CostParams params,
                                                   final CostCalculator<SocialCost, SocialCalculatorParams> socialCalculator,
                                                   final Map.Entry<Month, BigDecimal> salary,
                                                   final CostConfig configuration) {
        return socialCalculator.calculate(
                SocialCalculatorParams.builder()
                        .year(params.getYear())
                        .salary(salary.getValue())
                        .configuration(configuration)
                        .build());
    }

    private CostCalculator<SocialCost, SocialCalculatorParams> getSocialCalculator(final int year) {
        return this.socialCostCalculators.stream()
                .filter(calculator -> calculator.getYear() <= year)
                .max(Comparator.comparing(CostCalculator::getYear))
                .orElseThrow();
    }

    private CostCalculator<TaxCost, TaxCalculatorParams> getTaxCalculator(final int year) {
        return this.taxCostCalculators.stream()
                .filter(calculator -> calculator.getYear() <= year)
                .max(Comparator.comparing(CostCalculator::getYear))
                .orElseThrow();
    }
}
