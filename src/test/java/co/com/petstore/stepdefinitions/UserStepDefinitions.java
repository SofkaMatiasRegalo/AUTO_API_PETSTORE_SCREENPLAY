package co.com.petstore.stepdefinitions;

import co.com.petstore.models.User;
import co.com.petstore.tasks.CreateUser;
import co.com.petstore.utils.Constantes;
import co.com.petstore.utils.UserContext;
import io.cucumber.java.en.Given;

public class UserStepDefinitions {

    private final UserContext context = UserContext.current();

    @Given("que el actor tiene los datos de un nuevo usuario {string}")
    public void queElActorTieneLosDatasDe(String username) {
        context.usuario = User.builder()
            .id(Math.floorMod(username.hashCode(), 900000) + 100000)
            .username(username)
            .firstName(Constantes.USER_FIRST_NAME)
            .lastName(Constantes.USER_LAST_NAME)
            .email(Constantes.USER_EMAIL)
            .password(Constantes.USER_PASSWORD)
            .phone(Constantes.USER_PHONE)
            .userStatus(Constantes.USER_STATUS_ACTIVE)
            .build();
    }

    @Given("existe un usuario registrado con username {string}")
    public void existeUnUsuarioRegistradoConUsername(String username) {
        queElActorTieneLosDatasDe(username);
        context.actor.attemptsTo(CreateUser.withData(context.usuario));
    }
}
