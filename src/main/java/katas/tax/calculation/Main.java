package katas.tax.calculation;

import katas.tax.calculation.application.PrintDetailedInvoice;
import katas.tax.calculation.functions.*;

public class Main {
    public static void main(String[] args) {
        final var printDetailedInvoice = new PrintDetailedInvoice(
                new CommandLinesParser(),
                new ComputeInvoiceLines(),
                new ComputeAmountIncludingAllTaxes(),
                new ComputeAmountOfTaxes(),
                new GenerateInvoiceText());

        final var input1 = """
                1 livre à 12.49
                1 CD musical à 14.99
                1 barre de chocolat à 0.85
                """;

        System.out.printf("Input 1 : %n%s%n", input1);

        final var output1 = printDetailedInvoice.print(input1);

        System.out.printf("Output 1 : %n%s%n%n", output1);

        final var input2 = """
                1 boîte de chocolats importée à 10.00
                1 flacon de parfum importé à 47.50
                """;

        System.out.printf("Input 2 : %n%s%n", input2);

        final var output2 = printDetailedInvoice.print(input2);

        System.out.printf("Output 2 : %n%s%n%n", output2);

        final var input3 = """
                1 flacon de parfum importé à 27.99
                1 flacon de parfum à 18.99
                1 boîte de pilules contre la migraine à 9.75
                1 boîte de chocolats importés à 11.25
                """;

        System.out.printf("Input 3 : %n%s%n", input3);

        final var output3 = printDetailedInvoice.print(input3);

        System.out.printf("Output 3 : %n%s%n%n", output3);
    }
}
