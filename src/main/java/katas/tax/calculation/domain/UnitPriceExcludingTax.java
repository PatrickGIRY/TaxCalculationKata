package katas.tax.calculation.domain;

import java.math.BigDecimal;

public record UnitPriceExcludingTax(BigDecimal value) {
    public UnitPriceExcludingTax(String value) {
        this(new BigDecimal(value));
    }
}
