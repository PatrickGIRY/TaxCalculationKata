package katas.tax.calculation.domain;

import java.math.BigDecimal;

public record AmountIncludesAllTaxes(BigDecimal value) {
    public AmountIncludesAllTaxes(String value) {
        this(new BigDecimal(value));
    }
}
