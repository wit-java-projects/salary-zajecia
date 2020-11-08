package pl.edu.wit.calculators.salary.cost;

import pl.edu.wit.calculators.salary.cost.model.MonthCost;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Kalkulator kosztow.
 * Kalkulator kosztow socialnych i podatkowych na dpostawie podanego roku podatkowego.
 *
 * @author pawel.kowalski
 */
public abstract class CostCalculator {

    /**
     * pobiera rok konfiguracji kalkulacji.
     *
     * @return rok konfiguracji kalkulacji.
     */
    public abstract int getYear();

    /**
     * zwraca liste przeliczonych kosztow wynagrodzenia.
     *
     * @param params parametry kalkulacji
     * @return lista kosztow.
     */
    public final List<MonthCost> calculator(final CalculationParams params) {
        return calculate(params);
    }

    protected abstract List<MonthCost> calculate(CalculationParams params);

    protected final BigDecimal calculateValue(final BigDecimal base, final double percent) {
        BigDecimal percentDecimal = BigDecimal.valueOf(percent);

        return base.multiply(percentDecimal)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
    }

}
