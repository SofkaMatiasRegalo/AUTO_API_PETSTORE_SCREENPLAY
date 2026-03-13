package co.com.petstore.tasks;

import co.com.petstore.utils.Constantes;
import co.com.petstore.utils.UserResources;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetUser implements Task {

    private final String username;

    public GetUser(String username) {
        this.username = username;
    }

    public static GetUser withUsername(String username) {
        return instrumented(GetUser.class, username);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Get.resource(UserResources.USER_BY_USERNAME)
                .with(request -> request
                    .pathParam(Constantes.PATH_PARAM_USERNAME, username))
        );
    }
}
