package co.com.petstore.hooks;

import co.com.petstore.utils.Constantes;
import co.com.petstore.utils.UserContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

public class Hooks {

    private final UserContext context = UserContext.current();

    @Before
    public void setUp() {
        OnStage.setTheStage(new OnlineCast());
        context.actor = Actor.named(Constantes.ACTOR_NAME).whoCan(CallAnApi.at(Constantes.BASE_URL));
    }

    @After
    public void tearDown() {
        UserContext.clear();
    }
}
