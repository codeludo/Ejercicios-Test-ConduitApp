package questions;

import lombok.AllArgsConstructor;
import models.GlobalFeed;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.MatcherAssert.assertThat;

@AllArgsConstructor
public class VerifyArticles implements Question<Boolean> {

    public static VerifyArticles inGlobalFeedPage() {
        return new VerifyArticles();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        GlobalFeed globalFeed = SerenityRest.lastResponse()
                .jsonPath()
                .getObject("data", GlobalFeed.class);
        return globalFeed.getArticlesCount() == 3;
    }
}
