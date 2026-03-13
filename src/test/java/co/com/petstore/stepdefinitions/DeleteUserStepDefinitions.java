package co.com.petstore.stepdefinitions;

import co.com.petstore.tasks.DeleteUser;
import co.com.petstore.utils.UserContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class DeleteUserStepDefinitions {

    private final UserContext context = UserContext.current();

    @When("el actor elimina el usuario {string} mediante DELETE")
    public void elActorEliminaElUsuario(String username) {
        context.actor.attemptsTo(DeleteUser.withUsername(username));
    }

    @Then("la respuesta de eliminacion es exitosa con codigo {int}")
    public void laRespuestaDeEliminacionEsExitosaConCodigo(int statusCode) {
        context.actor.should(
            seeThatResponse("La eliminacion del usuario devuelve HTTP " + statusCode,
                response -> response.statusCode(statusCode))
        );
    }
}
