package katas.tax.calculation.functions;

import katas.tax.calculation.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ComputeInvoiceLinesTest {

    private ComputeInvoiceLines computeInvoiceLines;

    public static Stream<Arguments> commandLineWithTaxExemptProduct() {
        return Stream.of(
                Arguments.of(new TestCase(
                        new CommandLine(
                                new Quatity(1),
                                new Product("livre"),
                                new UnitPriceExcludingTax("12.49")),
                        new AmountOfTaxes("0.00"),
                        new PriceIncludingAllTaxes("12.49"))),
                Arguments.of(new TestCase(
                        new CommandLine(
                                new Quatity(1),
                                new Product("barre de chocolat"),
                                new UnitPriceExcludingTax("0.85")),
                        new AmountOfTaxes("0.00"),
                        new PriceIncludingAllTaxes("0.85")))
                );
    }

    public static Stream<Arguments> commandLineWithImportedTaxExemptProduct() {
        return Stream.of(
                Arguments.of(
                        new TestCase(
                                new CommandLine(
                                        new Quatity(1),
                                        new Product("boîte de chocolats importée"),
                                        new UnitPriceExcludingTax("10.00")),
                                new AmountOfTaxes("0.50"),
                                new PriceIncludingAllTaxes("10.50")))
        );
    }

    public static Stream<Arguments> commandLineWithImportedTaxedProduct() {
        return Stream.of(
                Arguments.of(
                        new TestCase(
                                new CommandLine(
                                        new Quatity(1),
                                        new Product("flacon de parfum importé"),
                                        new UnitPriceExcludingTax("47.50")),
                                new AmountOfTaxes("7.15"),
                                new PriceIncludingAllTaxes("54.65")))
        );
    }

    public static Stream<Arguments> commandLineWithTaxedProduct() {
        return Stream.of(
                Arguments.of(
                        new TestCase(
                                new CommandLine(
                                        new Quatity(1),
                                        new Product("flacon de parfum"),
                                        new UnitPriceExcludingTax("18.99")),
                                new AmountOfTaxes("1.90"),
                                new PriceIncludingAllTaxes("20.89")),
                        new TestCase(
                                new CommandLine(
                                        new Quatity(1),
                                        new Product("CD musical"),
                                        new UnitPriceExcludingTax("14.99")),
                                new AmountOfTaxes("1.50"),
                                new PriceIncludingAllTaxes("16.49")))
        );
    }

    @BeforeEach
    void setUp() {
        computeInvoiceLines = new ComputeInvoiceLines();
    }

    @ParameterizedTest
    @MethodSource("commandLineWithTaxExemptProduct")
    void transform_command_line_to_invoice_line_with_tax_exempt_product(TestCase testCase) {

        final var commandLine = testCase.commandLine();
        final var commandLines = List.of(commandLine);

        final var invoiceLines = computeInvoiceLines.compute(commandLines);

        assertThat(invoiceLines).containsExactly(
                new InvoiceLine(
                        commandLine.quatity(),
                        new TaxExemptProduct(commandLine.product().label()),
                        testCase.amountOfTaxes(),
                        testCase.priceIncludingAllTaxes())
        );
    }

    @ParameterizedTest
    @MethodSource("commandLineWithImportedTaxExemptProduct")
    void transform_command_line_to_invoice_line_with_imported_tax_exempt_product(TestCase testCase) {

        final var commandLine = testCase.commandLine();
        final var commandLines = List.of(commandLine);

        final var invoiceLines = computeInvoiceLines.compute(commandLines);

        assertThat(invoiceLines).containsExactly(
                new InvoiceLine(
                        commandLine.quatity(),
                        new ImportedProduct(new TaxExemptProduct(commandLine.product().label())),
                        testCase.amountOfTaxes(),
                        testCase.priceIncludingAllTaxes())
        );
    }

    @ParameterizedTest
    @MethodSource("commandLineWithImportedTaxedProduct")
    void transform_command_line_to_invoice_line_with_imported_taxed_product(TestCase testCase) {

        final var commandLine = testCase.commandLine();
        final var commandLines = List.of(commandLine);

        final var invoiceLines = computeInvoiceLines.compute(commandLines);

        assertThat(invoiceLines).containsExactly(
                new InvoiceLine(
                        commandLine.quatity(),
                        new ImportedProduct(new TaxedProduct(commandLine.product().label())),
                        testCase.amountOfTaxes(),
                        testCase.priceIncludingAllTaxes())
        );
    }

    @ParameterizedTest
    @MethodSource("commandLineWithTaxedProduct")
    void transform_command_line_to_invoice_line_with_taxed_product(TestCase testCase) {

        final var commandLine = testCase.commandLine();
        final var commandLines = List.of(commandLine);

        final var invoiceLines = computeInvoiceLines.compute(commandLines);

        assertThat(invoiceLines).containsExactly(
                new InvoiceLine(
                        commandLine.quatity(),
                        new TaxedProduct(commandLine.product().label()),
                        testCase.amountOfTaxes(),
                        testCase.priceIncludingAllTaxes())
        );
    }

    private record TestCase(CommandLine commandLine, AmountOfTaxes amountOfTaxes, PriceIncludingAllTaxes priceIncludingAllTaxes) {
    }
}