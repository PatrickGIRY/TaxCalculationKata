package katas.tax.calculation.features.step.definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class FeatureStepDefinitions {
    @Given("state")
    public void state() {

    }
    @When("action")
    public void action() {

    }
    @Then("result")
    public void result() {
        assertThat(true).isFalse();
    }
}
