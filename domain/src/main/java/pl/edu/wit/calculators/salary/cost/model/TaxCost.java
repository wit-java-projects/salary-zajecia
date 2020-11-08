package pl.edu.wit.calculators.salary.cost.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class TaxCost {

    private final BigDecimal baseTaxSalary;
    private final BigDecimal taxAdvanced;
}
