package katas.tax.calculation.functions;

import java.math.BigDecimal;

public record AmountOfTaxes(BigDecimal value) {
    public AmountOfTaxes(String value) {
        this(new BigDecimal(value));
    }
}

