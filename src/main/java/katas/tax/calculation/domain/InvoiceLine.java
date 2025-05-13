package katas.tax.calculation.domain;

public record InvoiceLine(Quatity quatity, Product product, PriceIncludingAllTaxes priceIncludingAllTaxes) {
}
