package co.com.petstore.stepdefinitions;

import co.com.petstore.models.User;
import co.com.petstore.questions.UserResponse;
import co.com.petstore.tasks.CreateUser;
import co.com.petstore.tasks.DeleteUser;
import co.com.petstore.tasks.GetUser;
import co.com.petstore.tasks.UpdateUser;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class UserStepDefinitions {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    private Actor actor;
    private User usuario;

    @Before
    public void setUp() {
        OnStage.setTheStage(new OnlineCast());
        actor = Actor.named("QA Tester").whoCan(CallAnApi.at(BASE_URL));
    }

    @Given("que el actor tiene los datos de un nuevo usuario {string}")
    public void queElActorTieneLosDatasDe(String username) {
        usuario = User.builder()
            .id(99901)
            .username(username)
            .firstName("Test")
            .lastName("Screenplay")
            .email("test.screenplay@petstore.qa")
            .password("P@$$wordS3guro")
            .phone("3001234567")
            .userStatus(1)
            .build();
    }

    @When("el actor crea el usuario mediante POST")
    public void elActorCreaElUsuarioMediantePost() {
        actor.attemptsTo(CreateUser.withData(usuario));
    }

    @Then("la respuesta de creacion es exitosa con codigo {int}")
    public void laRespuestaDeCreacionEsExitosaConCodigo(int statusCode) {
        actor.should(
            seeThatResponse("La creacion del usuario devuelve HTTP " + statusCode,
                response -> response.statusCode(statusCode))
        );
    }

    @When("el actor consulta los datos del usuario {string} mediante GET")
    public void elActorConsultaLosDatosDelUsuario(String username) {
        actor.attemptsTo(GetUser.withUsername(username));
    }

    @Then("el username del usuario devuelto es {string}")
    public void elUsernameDelUsuarioDevueltoEs(String expectedUsername) {
        actor.should(
            seeThatResponse("El GET devuelve 200",
                response -> response.statusCode(200)),
            seeThat("El username en el body coincide con el registrado",
                UserResponse.field("username"), equalTo(expectedUsername))
        );
    }

    @When("el actor actualiza la informacion del usuario {string} mediante PUT")
    public void elActorActualizaLaInformacionDelUsuario(String username) {
        User updatedUser = User.builder()
            .id(usuario.getId())
            .username(username)
            .firstName("Updated")
            .lastName("Screenplay")
            .email("updated.screenplay@petstore.qa")
            .password(usuario.getPassword())
            .phone("3009876543")
            .userStatus(1)
            .build();
        actor.attemptsTo(UpdateUser.withData(username, updatedUser));
    }

    @Then("la respuesta de actualizacion es exitosa con codigo {int}")
    public void laRespuestaDeActualizacionEsExitosaConCodigo(int statusCode) {
        actor.should(
            seeThatResponse("La actualizacion del usuario devuelve HTTP " + statusCode,
                response -> response.statusCode(statusCode))
        );
    }

    @When("el actor elimina el usuario {string} mediante DELETE")
    public void elActorEliminaElUsuario(String username) {
        actor.attemptsTo(DeleteUser.withUsername(username));
    }

    @Then("la respuesta de eliminacion es exitosa con codigo {int}")
    public void laRespuestaDeEliminacionEsExitosaConCodigo(int statusCode) {
        actor.should(
            seeThatResponse("La eliminacion del usuario devuelve HTTP " + statusCode,
                response -> response.statusCode(statusCode))
        );
    }
}
