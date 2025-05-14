package katas.tax.calculation.functions;

import katas.tax.calculation.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComputeAmountOfTaxesTest {


    private ComputeAmountOfTaxes computeAmountOfTaxes;

    @BeforeEach
    void setUp() {
        computeAmountOfTaxes = new ComputeAmountOfTaxes();
    }

    @Test
    void compute_amount_of_taxes_with_no_invoice_line() {

        List<InvoiceLine> invoiceLines = List.of();

        final var amountOfTaxes = computeAmountOfTaxes.compute(invoiceLines);

        assertThat(amountOfTaxes).isEqualTo(new AmountOfTaxes("0.00"));
    }

    @Test
    void compute_amount_include_all_taxes_with_one_invoice_line() {

        List<InvoiceLine> invoiceLines = List.of(
                new InvoiceLine(
                        new Quatity(1),
                        new TaxedProduct("CD musical"),
                        new AmountOfTaxes("1.50"),
                        new PriceIncludingAllTaxes("14.99"))
        );

        final var amountOfTaxes = computeAmountOfTaxes.compute(invoiceLines);

        assertThat(amountOfTaxes).isEqualTo(new AmountOfTaxes("1.50"));
    }

    @Test
    void compute_amount_include_all_taxes_with_two_invoice_lines() {

        List<InvoiceLine> invoiceLines = List.of(
                new InvoiceLine(
                        new Quatity(1),
                        new TaxedProduct("flacon de parfum"),
                        new AmountOfTaxes("1.90"),
                        new PriceIncludingAllTaxes("2.50")),
                new InvoiceLine(
                        new Quatity(1),
                        new TaxedProduct("Chocolat"),
                        new AmountOfTaxes("1.50"),
                        new PriceIncludingAllTaxes("4.50"))
        );

        final var amountOfTaxes = computeAmountOfTaxes.compute(invoiceLines);

        assertThat(amountOfTaxes).isEqualTo(new AmountOfTaxes("3.40"));
    }
}