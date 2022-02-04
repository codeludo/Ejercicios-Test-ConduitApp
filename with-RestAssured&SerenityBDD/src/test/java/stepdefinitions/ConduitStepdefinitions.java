package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;
import org.hamcrest.Matchers;
import questions.VerifyArticles;
import tasks.RequestGlobalFeed;

import static net.serenitybdd.screenplay.actors.OnStage.*;

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
        setTheStage(new OnlineCast());
        theActorCalled(string).whoCan(CallAnApi.at(conduitRestApiBaseUrl));
    }
    @When("he request the global feed in conduit home page")
    public void he_request_the_global_feed_in_conduit_home_page() {
        theActorInTheSpotlight().attemptsTo(RequestGlobalFeed.inArticleRestApiService());
    }
    @Then("he only can see three articles")
    public void he_only_can_see_three_articles() {
        theActorInTheSpotlight().should(GivenWhenThen.seeThat(VerifyArticles.inGlobalFeedPage(),
                Matchers.equalTo(3)));
    }

}
