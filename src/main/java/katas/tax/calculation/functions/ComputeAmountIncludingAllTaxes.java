package katas.tax.calculation.functions;

import katas.tax.calculation.domain.AmountIncludesAllTaxes;
import katas.tax.calculation.domain.InvoiceLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ComputeAmountIncludingAllTaxes {
    public AmountIncludesAllTaxes compute(List<InvoiceLine> invoiceLines) {
        var result = BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR);
        for (final var invoiceLine : invoiceLines) {
            final var priceIncludingAllTaxes = invoiceLine.priceIncludingAllTaxes();
            result = result.add(priceIncludingAllTaxes.value());
        }
        return new AmountIncludesAllTaxes(result);
    }
}
