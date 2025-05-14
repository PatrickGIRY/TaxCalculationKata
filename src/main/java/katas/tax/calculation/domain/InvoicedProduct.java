package katas.tax.calculation.domain;

public sealed interface InvoicedProduct permits TaxedProduct, TaxExemptProduct, ImportedProduct {
    String label();
}
