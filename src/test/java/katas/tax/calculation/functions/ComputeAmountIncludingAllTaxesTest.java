package katas.tax.calculation.functions;

import katas.tax.calculation.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComputeAmountIncludingAllTaxesTest {

    private ComputeAmountIncludingAllTaxes computeAmountIncludingAllTaxes;

    @BeforeEach
    void setUp() {
        computeAmountIncludingAllTaxes = new ComputeAmountIncludingAllTaxes();
    }

    @Test
    void compute_amount_include_all_taxes_with_no_invoice_line() {

        List<InvoiceLine> invoiceLines = List.of();

        final var amountIncludesAllTaxes = computeAmountIncludingAllTaxes.compute(invoiceLines);

        assertThat(amountIncludesAllTaxes).isEqualTo(new AmountIncludesAllTaxes("0.00"));
    }

    @Test
    void compute_amount_include_all_taxes_with_one_invoice_line() {

        List<InvoiceLine> invoiceLines = List.of(
                new InvoiceLine(
                        new Quatity(1),
                        new TaxedProduct("Livre"),
                        new AmountOfTaxes("0.00"),
                        new PriceIncludingAllTaxes("2.50"))
        );

        final var amountIncludesAllTaxes = computeAmountIncludingAllTaxes.compute(invoiceLines);

        assertThat(amountIncludesAllTaxes).isEqualTo(new AmountIncludesAllTaxes("2.50"));
    }

    @Test
    void compute_amount_include_all_taxes_with_two_invoice_lines() {

        List<InvoiceLine> invoiceLines = List.of(
                new InvoiceLine(
                        new Quatity(1),
                        new TaxedProduct("Livre"),
                        new AmountOfTaxes("0.00"),
                        new PriceIncludingAllTaxes("2.50")),
                new InvoiceLine(
                        new Quatity(1),
                        new TaxedProduct("Chocolat"),
                        new AmountOfTaxes("0.00"),
                        new PriceIncludingAllTaxes("4.50"))
        );

        final var amountIncludesAllTaxes = computeAmountIncludingAllTaxes.compute(invoiceLines);

        assertThat(amountIncludesAllTaxes).isEqualTo(new AmountIncludesAllTaxes("7.00"));
    }
}