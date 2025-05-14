package katas.tax.calculation.functions;

import katas.tax.calculation.domain.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

public class ComputeInvoiceLines {

    private static final BigDecimal IMPORT_ADDITIONAL_TAX_RATE = new BigDecimal("5");
    private static final BigDecimal TAX_RATE = new BigDecimal("10");
    private static final BigDecimal HUNDRED = new BigDecimal("100");
    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR);
    private static final String[] EXEMPT_TAX_LABEL_CONTAINS = {
            "livre",
            "chocolat",
            "pilules"
    };
    private static final BigDecimal INCREMENT = new BigDecimal("0.05");

    public List<InvoiceLine> compute(List<CommandLine> commandLines) {
        return commandLines.stream()
                .map(ComputeInvoiceLines::toInvoiceLine)
                .toList();
    }

    private static InvoiceLine toInvoiceLine(CommandLine commandLine) {
        final var invoicedProduct = toInvoicedProduct(commandLine);
        final var quatity = commandLine.quatity();
        final var priceExcludingTax = computePriceExcludingTax(quatity, commandLine.unitPriceExcludingTax());
        final var amountOfTax = toAmountOfTax(invoicedProduct, priceExcludingTax);
        return new InvoiceLine(quatity, invoicedProduct, amountOfTax, toPriceIncludingAllTaxes(priceExcludingTax, amountOfTax));
    }

    private static AmountOfTaxes toAmountOfTax(InvoicedProduct invoicedProduct, BigDecimal priceExcludingTax) {
        return new AmountOfTaxes(computeAmountOfTax(invoicedProduct, priceExcludingTax));
    }

    private static BigDecimal computeAmountOfTax(InvoicedProduct invoicedProduct, BigDecimal priceExcludingTax) {
        final var taxAmount = computeTaxAmount(invoicedProduct, priceExcludingTax);
        final var additionalTaxAmount = computeAdditionalTaxAmount(invoicedProduct, priceExcludingTax);
        return taxAmount.add(additionalTaxAmount);
    }

    private static PriceIncludingAllTaxes toPriceIncludingAllTaxes(BigDecimal priceExcludingTax, AmountOfTaxes amountOfTaxes) {
        return new PriceIncludingAllTaxes(computePriceIncludingAllTaxes(priceExcludingTax, amountOfTaxes));
    }

    private static BigDecimal computePriceExcludingTax(Quatity quatity, UnitPriceExcludingTax unitPriceExcludingTax) {
        return new BigDecimal(quatity.value()).multiply(unitPriceExcludingTax.value());
    }

    private static BigDecimal computePriceIncludingAllTaxes(BigDecimal priceExcludingTax, AmountOfTaxes amountOfTaxes) {
       return priceExcludingTax.add(amountOfTaxes.value());
    }

    private static BigDecimal computeAdditionalTaxAmount(InvoicedProduct invoicedProduct, BigDecimal priceExcludingTax) {
        return switch (invoicedProduct) {
            case ImportedProduct ignored -> roundUpToNearest5Cents(priceExcludingTax.multiply(IMPORT_ADDITIONAL_TAX_RATE).divide(HUNDRED, new MathContext(3)));
            case TaxExemptProduct ignored -> ZERO;
            case TaxedProduct ignored -> ZERO;
        };
    }

    private static BigDecimal computeTaxAmount(InvoicedProduct invoicedProduct, BigDecimal priceExcludingTax) {
        return switch (invoicedProduct) {
            case ImportedProduct importedProduct -> computeTaxAmount(importedProduct.invoicedProduct(), priceExcludingTax);
            case TaxExemptProduct ignored -> ZERO;
            case TaxedProduct ignored -> roundUpToNearest5Cents(priceExcludingTax.multiply(TAX_RATE).divide(HUNDRED, new MathContext(3)));
        };
    }

    private static BigDecimal roundUpToNearest5Cents(BigDecimal value) {
        final var rounded = value.divide(INCREMENT, new MathContext(2)).multiply(INCREMENT);
        return rounded.compareTo(value) < 0 ? rounded.add(INCREMENT) : rounded;
    }

    private static InvoicedProduct toInvoicedProduct(CommandLine commandLine) {
        final var product = commandLine.product();
        String productLabel = product.label();
        final var invoicedProduct = toInvoicedProduct(productLabel);
        if (isImportedProduct(productLabel)) {
            return new ImportedProduct(invoicedProduct);
        } else {
            return invoicedProduct;
        }
    }

    private static InvoicedProduct toInvoicedProduct(String productLabel) {
        return Stream.of(EXEMPT_TAX_LABEL_CONTAINS)
                .<InvoicedProduct>mapMulti((contentOfLabel, stream) -> {
                    if (productLabel.contains(contentOfLabel)) {
                        stream.accept(new TaxExemptProduct(productLabel));
                    }
                })
                .findFirst()
                .orElseGet(() -> new TaxedProduct(productLabel));
    }

    private static boolean isImportedProduct(String productLabel) {
        return productLabel.contains("importé");
    }
}
