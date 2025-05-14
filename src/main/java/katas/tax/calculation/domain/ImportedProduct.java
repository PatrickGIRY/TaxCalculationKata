package katas.tax.calculation.domain;

public record ImportedProduct(InvoicedProduct invoicedProduct) implements InvoicedProduct {
    @Override
    public String label() {
        return invoicedProduct.label();
    }
}
