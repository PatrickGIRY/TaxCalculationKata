package katas.tax.calculation.functions;

import katas.tax.calculation.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GenerateInvoiceTextTest {

    private GenerateInvoiceText generateInvoiceText;

    @BeforeEach
    void setUp() {
        generateInvoiceText = new GenerateInvoiceText();
    }

    @Test
    void generate_invoice_text() {

        final var invoiceLines = List.of(
                new InvoiceLine(
                        new Quatity(1),
                        new TaxedProduct("CD musical"),
                        new AmountOfTaxes("1.50"),
                        new PriceIncludingAllTaxes("14.99")
                )
        );
        final var amountOfTaxes = new AmountOfTaxes("1.50");
        final var amountIncludesAllTaxes = new AmountIncludesAllTaxes("14.99");

        final var report = generateInvoiceText.generate(invoiceLines, amountOfTaxes, amountIncludesAllTaxes);

        assertThat(report).isEqualTo("1 CD musical : 14.99 Montant des taxes : 1.50 Total : 14.99");
    }
}