package pl.edu.wit.calculators.salary.cost.model.parameters;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class CostParams {
    private final int year;
    private final Map<Month, BigDecimal> salary;
    private final boolean over26Years;
}
