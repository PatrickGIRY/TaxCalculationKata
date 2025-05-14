package katas.tax.calculation.domain;

import java.math.BigDecimal;

public record AmountOfTaxes(BigDecimal value) {
    public AmountOfTaxes(String value) {
        this(new BigDecimal(value));
    }
}

