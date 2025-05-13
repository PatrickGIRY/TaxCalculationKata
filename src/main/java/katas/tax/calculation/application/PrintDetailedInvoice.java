package katas.tax.calculation.application;

import katas.tax.calculation.functions.*;

public class PrintDetailedInvoice {
    private final CommandLinesParser commandLinesParser;
    private final ComputeInvoiceLines computeInvoiceLines;
    private final ComputeAmountIncludingAllTaxes computeAmountIncludingAllTaxes;
    private final ComputeAmountOfTaxes computeAmountOfTaxes;
    private final GenerateInvoiceText generateInvoiceText;

    public PrintDetailedInvoice(CommandLinesParser commandLinesParser,
                                ComputeInvoiceLines computeInvoiceLines,
                                ComputeAmountIncludingAllTaxes computeAmountIncludingAllTaxes,
                                ComputeAmountOfTaxes computeAmountOfTaxes,
                                GenerateInvoiceText generateInvoiceText) {
        this.commandLinesParser = commandLinesParser;
        this.computeInvoiceLines = computeInvoiceLines;
        this.computeAmountIncludingAllTaxes = computeAmountIncludingAllTaxes;
        this.computeAmountOfTaxes = computeAmountOfTaxes;
        this.generateInvoiceText = generateInvoiceText;
    }

    public String print(String input) {
        final var commandLines = commandLinesParser.parse(input);
        final var invoiceLines = computeInvoiceLines.compute(commandLines);
        final var amountIncludesAllTaxes = computeAmountIncludingAllTaxes.compute(invoiceLines);
        final var amountOfTaxes = computeAmountOfTaxes.compute(invoiceLines);
        return generateInvoiceText.generate(invoiceLines, amountOfTaxes, amountIncludesAllTaxes);
    }
}
