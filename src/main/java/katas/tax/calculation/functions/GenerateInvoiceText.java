package katas.tax.calculation.functions;

import katas.tax.calculation.domain.AmountIncludesAllTaxes;
import katas.tax.calculation.domain.AmountOfTaxes;
import katas.tax.calculation.domain.InvoiceLine;

import java.util.List;

public class GenerateInvoiceText {
    public String generate(List<InvoiceLine> invoiceLines, AmountOfTaxes amountOfTaxes, AmountIncludesAllTaxes amountIncludesAllTaxes) {
        final var report = new StringBuilder();
        for (final var invoiceLine: invoiceLines) {
            report.append(String.format("%d %s : %s ",
                    invoiceLine.quatity().value(),
                    invoiceLine.product().label(),
                    invoiceLine.priceIncludingAllTaxes().value()));
        }
        report.append(String.format("Montant des taxes : %s ", amountOfTaxes.value()));
        report.append(String.format("Total : %s", amountIncludesAllTaxes.value()));
        return report.toString();
    }
}
