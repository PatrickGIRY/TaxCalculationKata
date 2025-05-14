package katas.tax.calculation.functions;

import katas.tax.calculation.domain.AmountIncludesAllTaxes;
import katas.tax.calculation.domain.AmountOfTaxes;
import katas.tax.calculation.domain.InvoiceLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ComputeAmountOfTaxes {
    public AmountOfTaxes compute(List<InvoiceLine> invoiceLines) {
        var result = BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR);
        for (final var invoiceLine : invoiceLines) {
            final var amountOfTaxes = invoiceLine.amountOfTaxes();
            result = result.add(amountOfTaxes.value());
        }
        return new AmountOfTaxes(result);
    }
}
