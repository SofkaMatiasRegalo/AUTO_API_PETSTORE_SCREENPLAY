package co.com.petstore.tasks;

import co.com.petstore.models.User;
import co.com.petstore.utils.Constantes;
import co.com.petstore.utils.UserResources;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdateUser implements Task {

    private final String username;
    private final User updatedUser;

    public UpdateUser(String username, User updatedUser) {
        this.username = username;
        this.updatedUser = updatedUser;
    }

    public static UpdateUser withData(String username, User updatedUser) {
        return instrumented(UpdateUser.class, username, updatedUser);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Put.to(UserResources.USER_BY_USERNAME)
                .with(request -> request
                    .contentType(Constantes.CONTENT_TYPE_JSON)
                    .pathParam("username", username)
                    .body(updatedUser))
        );
    }
}
