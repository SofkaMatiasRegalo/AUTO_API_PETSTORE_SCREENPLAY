package co.com.petstore.stepdefinitions;

import co.com.petstore.tasks.CreateUser;
import co.com.petstore.utils.UserContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class CreateUserStepDefinitions {

    private final UserContext context = UserContext.current();

    @When("el actor crea el usuario mediante POST")
    public void elActorCreaElUsuarioMediantePost() {
        context.actor.attemptsTo(CreateUser.withData(context.usuario));
    }

    @Then("la respuesta de creacion es exitosa con codigo {int}")
    public void laRespuestaDeCreacionEsExitosaConCodigo(int statusCode) {
        context.actor.should(
            seeThatResponse("La creacion del usuario devuelve HTTP " + statusCode,
                response -> response.statusCode(statusCode))
        );
    }
}
