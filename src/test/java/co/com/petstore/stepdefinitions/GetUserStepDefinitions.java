package co.com.petstore.stepdefinitions;

import co.com.petstore.questions.UserResponse;
import co.com.petstore.tasks.GetUser;
import co.com.petstore.utils.Constantes;
import co.com.petstore.utils.UserContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class GetUserStepDefinitions {

    private final UserContext context = UserContext.current();

    @When("el actor consulta los datos del usuario {string} mediante GET")
    public void elActorConsultaLosDatosDelUsuario(String username) {
        context.actor.attemptsTo(GetUser.withUsername(username));
    }

    @Then("el username del usuario devuelto es {string}")
    public void elUsernameDelUsuarioDevueltoEs(String expectedUsername) {
        context.actor.should(
            seeThatResponse("El GET devuelve " + Constantes.STATUS_OK,
                response -> response.statusCode(Constantes.STATUS_OK)),
            seeThat("El username en el body coincide con el registrado",
                UserResponse.field(Constantes.JSON_PATH_USERNAME), equalTo(expectedUsername))
        );
    }

    @Then("el campo {string} del usuario devuelto es {string}")
    public void elCampoDelUsuarioDevueltoEs(String field, String expectedValue) {
        context.actor.should(
            seeThatResponse("El GET devuelve " + Constantes.STATUS_OK,
                response -> response.statusCode(Constantes.STATUS_OK)),
            seeThat("El campo " + field + " coincide con el valor esperado",
                UserResponse.field(field), equalTo(expectedValue))
        );
    }

    @Then("la consulta del usuario es fallida con codigo {int}")
    public void laConsultaDelUsuarioEsFallidaConCodigo(int statusCode) {
        context.actor.should(
            seeThatResponse("La consulta del usuario devuelve HTTP " + statusCode,
                response -> response.statusCode(statusCode))
        );
    }
}
