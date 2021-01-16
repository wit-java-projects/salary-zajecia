package pl.edu.wit.calculators.salary.cost;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Kalkulator kosztow.
 * Kalkulator kosztow socialnych i podatkowych na dpostawie podanego roku podatkowego.
 *
 * @author pawel.kowalski
 */
public abstract class CostCalculator<R, P extends CalculatorParams> {

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
    public final R calculate(final P params) {
        return calculateInternal(params);
    }

    protected abstract R calculateInternal(P params);

    protected final BigDecimal calculateValue(final BigDecimal base, final double percent) {
        final BigDecimal percentDecimal = BigDecimal.valueOf(percent);

        return formatValue(base.multiply(percentDecimal)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
    }

    protected BigDecimal formatValue(final BigDecimal base) {
        return base.setScale(2, RoundingMode.HALF_UP);
    }

}
