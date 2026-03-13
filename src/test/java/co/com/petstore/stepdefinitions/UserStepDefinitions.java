package co.com.petstore.stepdefinitions;

import co.com.petstore.models.User;
import co.com.petstore.questions.UserResponse;
import co.com.petstore.tasks.CreateUser;
import co.com.petstore.tasks.DeleteUser;
import co.com.petstore.tasks.GetUser;
import co.com.petstore.tasks.UpdateUser;
import co.com.petstore.utils.Constantes;
import co.com.petstore.utils.UserContext;
import co.com.petstore.utils.UserDataFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class UserStepDefinitions {

    private final UserContext context = UserContext.current();

    private Actor actor() {
        return context.getActor();
    }

    private User usuario() {
        return context.getUsuario();
    }

    private void assertStatusCode(String action, int statusCode) {
        actor().should(
            seeThatResponse(action + " devuelve HTTP " + statusCode,
                response -> response.statusCode(statusCode))
        );
    }

    private void inicializarUsuario(String username) {
        context.setUsuario(UserDataFactory.newUser(username));
    }

    @Given("que el actor tiene los datos de un nuevo usuario unico")
    public void queElActorTieneLosDatosDeUnNuevoUsuarioUnico() {
        String uniqueUsername = "screenplay_" + System.currentTimeMillis();
        inicializarUsuario(uniqueUsername);
    }

    @When("el actor crea el usuario mediante POST")
    public void elActorCreaElUsuarioMediantePost() {
        actor().attemptsTo(CreateUser.withData(usuario()));
    }

    @Then("la respuesta de creacion es exitosa con codigo {int}")
    public void laRespuestaDeCreacionEsExitosaConCodigo(int statusCode) {
        assertStatusCode("La creacion del usuario", statusCode);
    }

    @When("el actor consulta los datos del usuario creado mediante GET")
    public void elActorConsultaLosDatosDelUsuarioCreadoMedianteGet() {
        actor().attemptsTo(GetUser.withUsername(usuario().getUsername()));
    }

    @Then("el username del usuario devuelto coincide con el creado")
    public void elUsernameDelUsuarioDevueltoCoincideConElCreado() {
        actor().should(
            seeThatResponse("El GET devuelve HTTP " + Constantes.STATUS_OK,
                response -> response.statusCode(Constantes.STATUS_OK)),
            seeThat("El username en el body coincide con el usuario creado",
                UserResponse.field(Constantes.JSON_PATH_USERNAME), equalTo(usuario().getUsername()))
        );
    }

    @Then("el campo {string} del usuario devuelto es {string}")
    public void elCampoDelUsuarioDevueltoEs(String field, String expectedValue) {
        actor().should(
            seeThatResponse("El GET devuelve HTTP " + Constantes.STATUS_OK,
                response -> response.statusCode(Constantes.STATUS_OK)),
            seeThat("El campo " + field + " coincide con el valor esperado",
                UserResponse.field(field), equalTo(expectedValue))
        );
    }

    @Then("la consulta del usuario es fallida con codigo {int}")
    public void laConsultaDelUsuarioEsFallidaConCodigo(int statusCode) {
        assertStatusCode("La consulta del usuario", statusCode);
    }

    @When("el actor actualiza la informacion del usuario creado mediante PUT")
    public void elActorActualizaLaInformacionDelUsuarioCreadoMediantePut() {
        String username = usuario().getUsername();
        User usuarioActualizado = UserDataFactory.updatedUserFrom(usuario(), username);
        actor().attemptsTo(UpdateUser.withData(username, usuarioActualizado));
        context.setUsuario(usuarioActualizado);
    }

    @Then("la respuesta de actualizacion es exitosa con codigo {int}")
    public void laRespuestaDeActualizacionEsExitosaConCodigo(int statusCode) {
        assertStatusCode("La actualizacion del usuario", statusCode);
    }

    @When("el actor elimina el usuario creado mediante DELETE")
    public void elActorEliminaElUsuarioCreadoMedianteDelete() {
        actor().attemptsTo(DeleteUser.withUsername(usuario().getUsername()));
    }

    @Then("la respuesta de eliminacion es exitosa con codigo {int}")
    public void laRespuestaDeEliminacionEsExitosaConCodigo(int statusCode) {
        assertStatusCode("La eliminacion del usuario", statusCode);
    }
}
