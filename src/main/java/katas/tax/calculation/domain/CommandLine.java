package katas.tax.calculation.domain;

public record CommandLine(Quatity quatity, Product product, UnitPriceExcludingTax unitPriceExcludingTax) {
}
