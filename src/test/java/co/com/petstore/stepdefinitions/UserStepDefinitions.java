package co.com.petstore.stepdefinitions;

import co.com.petstore.models.User;
import co.com.petstore.tasks.CreateUser;
import co.com.petstore.utils.UserContext;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

public class UserStepDefinitions {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    private final UserContext context;

    public UserStepDefinitions(UserContext context) {
        this.context = context;
    }

    @Before
    public void setUp() {
        OnStage.setTheStage(new OnlineCast());
        context.actor = Actor.named("QA Tester").whoCan(CallAnApi.at(BASE_URL));
    }

    @Given("que el actor tiene los datos de un nuevo usuario {string}")
    public void queElActorTieneLosDatasDe(String username) {
        context.usuario = User.builder()
            .id(Math.floorMod(username.hashCode(), 900000) + 100000)
            .username(username)
            .firstName("Test")
            .lastName("Screenplay")
            .email("test.screenplay@petstore.qa")
            .password("P@$$wordS3guro")
            .phone("3001234567")
            .userStatus(1)
            .build();
    }

    @Given("existe un usuario registrado con username {string}")
    public void existeUnUsuarioRegistradoConUsername(String username) {
        queElActorTieneLosDatasDe(username);
        context.actor.attemptsTo(CreateUser.withData(context.usuario));
    }

    
}
