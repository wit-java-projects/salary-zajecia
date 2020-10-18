package pl.edu.wit.calculators.salary.cost;

import java.util.Collections;
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
    public List calculator(final CalculationParams params) {
        return Collections.emptyList();
    }
}
