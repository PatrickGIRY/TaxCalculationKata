package katas.tax.calculation.application;

import katas.tax.calculation.domain.*;
import katas.tax.calculation.functions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PrintDetailedInvoiceTest {

    private static final String INPUT = "1 livre à 12.49";
    @Mock
    private CommandLinesParser commandLinesParser;
    @Mock
    private ComputeInvoiceLines computeInvoiceLines;
    @Mock
    private ComputeAmountIncludingAllTaxes computeAmountIncludingAllTaxes;
    @Mock
    private ComputeAmountOfTaxes computeAmountOfTaxes;
    @Mock
    private GenerateInvoiceText generateInvoiceText;

    private PrintDetailedInvoice printDetailedInvoice;

    private List<CommandLine> commandLines;

    private List<InvoiceLine> invoiceLines;


    @BeforeEach
    void setUp() {
        printDetailedInvoice = new PrintDetailedInvoice(
                commandLinesParser,
                computeInvoiceLines,
                computeAmountIncludingAllTaxes,
                computeAmountOfTaxes,
                generateInvoiceText);

        commandLines = List.of(new CommandLine(new Quatity(1), new Product("Livre"), new UnitPriceExcludingTax("12.49")));
        invoiceLines = List.of(new InvoiceLine(new Quatity(1), new Product("Livre"), new PriceIncludingAllTaxes("12.49")));
    }

    @Test
    void should_parse_command_lines() {

        printDetailedInvoice.print(INPUT);

        then(commandLinesParser).should()
                .parse(INPUT);
    }

    @Test
    void should_compute_all_taxes_includes_price_for_each_lines() {

        given(commandLinesParser.parse(INPUT))
                .willReturn(commandLines);

        printDetailedInvoice.print(INPUT);

        then(computeInvoiceLines).should()
                .compute(commandLines);
    }

    @Test
    void should_compute_the_amount_including_all_taxes() {

        given(commandLinesParser.parse(INPUT))
                .willReturn(commandLines);
        given(computeInvoiceLines.compute(commandLines))
                .willReturn(invoiceLines);

        printDetailedInvoice.print(INPUT);

        then(computeAmountIncludingAllTaxes).should()
                .compute(invoiceLines);
    }

    @Test
    void should_compute_amount_of_taxes() {
        given(commandLinesParser.parse(INPUT))
                .willReturn(commandLines);
        given(computeInvoiceLines.compute(commandLines))
                .willReturn(invoiceLines);

        printDetailedInvoice.print(INPUT);

        then(computeAmountOfTaxes).should()
                .compute(invoiceLines);
    }

    @Test
    void should_generate_invoice_text() {
        final var amountIncludesAllTaxes = new AmountIncludesAllTaxes("12.49");
        final var amountOfTaxes = new AmountOfTaxes("0.00");
        final var expectedInvoiceText = "1 livre : 12.49 Montant des taxes : 0.00 Total : 12.49";

        given(commandLinesParser.parse(INPUT))
                .willReturn(commandLines);
        given(computeInvoiceLines.compute(commandLines))
                .willReturn(invoiceLines);
        given(computeAmountIncludingAllTaxes.compute(invoiceLines))
                .willReturn(amountIncludesAllTaxes);
        given(computeAmountOfTaxes.compute(invoiceLines))
                .willReturn(amountOfTaxes);
        given(generateInvoiceText.generate(invoiceLines, amountOfTaxes, amountIncludesAllTaxes))
                .willReturn(expectedInvoiceText);

        final var invoiceText = printDetailedInvoice.print(INPUT);

        then(generateInvoiceText)
                .should()
                .generate(invoiceLines, amountOfTaxes, amountIncludesAllTaxes);

        assertThat(invoiceText).isEqualTo(expectedInvoiceText);
    }
}