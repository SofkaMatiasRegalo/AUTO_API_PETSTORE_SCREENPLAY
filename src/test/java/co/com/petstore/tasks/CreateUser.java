package co.com.petstore.tasks;

import co.com.petstore.models.User;
import co.com.petstore.utils.UserResources;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateUser implements Task {

    private final User user;

    public CreateUser(User user) {
        this.user = user;
    }

    public static CreateUser withData(User user) {
        return instrumented(CreateUser.class, user);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Post.to(UserResources.USER_ENDPOINT)
                .with(request -> request
                    .contentType("application/json")
                    .body(user))
        );
    }
}
