package pl.edu.wit.calculators.salary.cost.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Month;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class MonthCost {
    private final Month month;
    private final BigDecimal grossSalary;
    private final BigDecimal netSalary;
    private final SocialCost socialCost;
    private final TaxCost taxCost;
}
