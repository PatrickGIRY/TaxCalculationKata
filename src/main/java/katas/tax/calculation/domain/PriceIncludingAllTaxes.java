package katas.tax.calculation.domain;

import java.math.BigDecimal;

public record PriceIncludingAllTaxes(BigDecimal value) {
    public PriceIncludingAllTaxes(String value) {
        this(new BigDecimal(value));
    }
}
