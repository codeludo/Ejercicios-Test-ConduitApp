package tasks;

import lombok.AllArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

@AllArgsConstructor
public class RequestGlobalFeed implements Task {

    public static RequestGlobalFeed inArticleRestApiService() {
        return instrumented(RequestGlobalFeed.class);
    }


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/articles?limit=10&offset=0")
        );
    }
}
