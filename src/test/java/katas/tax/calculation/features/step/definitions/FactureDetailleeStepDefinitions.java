package katas.tax.calculation.features.step.definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import katas.tax.calculation.application.PrintDetailedInvoice;
import katas.tax.calculation.functions.*;
import org.assertj.core.api.Assertions;

public class FactureDetailleeStepDefinitions {
    private String commandLines;
    private String DetailedInvoice;

    @Given("on paase commande de :")
    public void on_paase_commande_de(String commandLines) {
        this.commandLines = commandLines;
    }

    @When("on imprime la facture détaillée")
    public void on_imprime_la_facture_detaillee() {
        final var printDetailedInvoice = new PrintDetailedInvoice(
                new CommandLinesParser(),
                new ComputeInvoiceLines(),
                new ComputeAmountIncludingAllTaxes(),
                new ComputeAmountOfTaxes(),
                new GenerateInvoiceText());
        this.DetailedInvoice = printDetailedInvoice.print(commandLines);
    }

    @Then("on obtient :")
    public void on_obtient(String expectedDetailedInvoice) {
       Assertions.assertThat(DetailedInvoice).isEqualTo(expectedDetailedInvoice);
    }

}
