package co.com.petstore.hooks;

import co.com.petstore.tasks.DeleteUser;
import co.com.petstore.utils.Constantes;
import co.com.petstore.utils.UserContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

public class Hooks {

    @Before
    public void setUp() {
        UserContext.clear();
        OnStage.setTheStage(new OnlineCast());
        UserContext context = UserContext.current();
        context.setActor(Actor.named(Constantes.ACTOR_NAME).whoCan(CallAnApi.at(Constantes.BASE_URL)));
    }

    @After
    public void tearDown() {
        UserContext context = UserContext.current();
        if (context.getActor() != null && context.getUsuario() != null && context.getUsuario().getUsername() != null) {
            context.getActor().attemptsTo(DeleteUser.withUsername(context.getUsuario().getUsername()));
        }
        UserContext.clear();
    }
}
