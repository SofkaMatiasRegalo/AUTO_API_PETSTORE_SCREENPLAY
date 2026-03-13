package co.com.petstore.stepdefinitions;

import co.com.petstore.models.User;
import co.com.petstore.tasks.UpdateUser;
import co.com.petstore.utils.Constantes;
import co.com.petstore.utils.UserContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class UpdateUserStepDefinitions {

    private final UserContext context = UserContext.current();

    @When("el actor actualiza la informacion del usuario {string} mediante PUT")
    public void elActorActualizaLaInformacionDelUsuario(String username) {
        User usuarioActualizado = User.builder()
            .id(context.usuario.getId())
            .username(username)
            .firstName(Constantes.USER_UPDATED_FIRST_NAME)
            .lastName(Constantes.USER_LAST_NAME)
            .email(Constantes.USER_UPDATED_EMAIL)
            .password(context.usuario.getPassword())
            .phone(Constantes.USER_UPDATED_PHONE)
            .userStatus(Constantes.USER_STATUS_ACTIVE)
            .build();
        context.actor.attemptsTo(UpdateUser.withData(username, usuarioActualizado));
    }

    @Then("la respuesta de actualizacion es exitosa con codigo {int}")
    public void laRespuestaDeActualizacionEsExitosaConCodigo(int statusCode) {
        context.actor.should(
            seeThatResponse("La actualizacion del usuario devuelve HTTP " + statusCode,
                response -> response.statusCode(statusCode))
        );
    }
}
