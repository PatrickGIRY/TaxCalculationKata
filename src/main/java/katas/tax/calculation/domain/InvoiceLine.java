package katas.tax.calculation.domain;

public record InvoiceLine(
        Quatity quatity,
        InvoicedProduct product,
        AmountOfTaxes amountOfTaxes,
        PriceIncludingAllTaxes priceIncludingAllTaxes
) {
}
