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
public class SocialCost {
    private final BigDecimal pension;
    private final BigDecimal disability;
    private final BigDecimal sickness;
    private final BigDecimal medical;

    public BigDecimal getTotal() {
        return pension.add(disability).add(sickness)
                .add(medical);
    }

    public BigDecimal getDeductibleSocialCost() {
        return pension.add(disability).add(sickness);
    }
}
