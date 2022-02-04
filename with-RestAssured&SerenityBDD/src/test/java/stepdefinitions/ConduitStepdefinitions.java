package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class ConduitStepdefinitions {

    private String conduitRestApiBaseUrl;
    private EnvironmentVariables environmentVariables;
    private Actor actor;

    @Before
    public void configureBaseUrl(){
        conduitRestApiBaseUrl = environmentVariables.optionalProperty("baseurl")
                .orElse("https://api.realworld.io/api");
    }

    @Given("that {string} can request conduit app rest services")
    public void that_can_request_conduit_app_rest_services(String string) {
        
    }
    @When("he request the global feed in conduit home page")
    public void he_request_the_global_feed_in_conduit_home_page() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("he only can see three articles")
    public void he_only_can_see_three_articles() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
