package co.com.petstore.tasks;

import co.com.petstore.utils.UserResources;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeleteUser implements Task {

    private final String username;

    public DeleteUser(String username) {
        this.username = username;
    }

    public static DeleteUser withUsername(String username) {
        return instrumented(DeleteUser.class, username);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Delete.from(UserResources.USER_BY_USERNAME)
                .with(request -> request
                    .pathParam("username", username))
        );
    }
}
