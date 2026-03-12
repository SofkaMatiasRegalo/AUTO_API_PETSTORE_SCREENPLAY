package co.com.petstore.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class UserResponse implements Question<String> {

    private final String jsonPath;

    private UserResponse(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public static UserResponse field(String jsonPath) {
        return new UserResponse(jsonPath);
    }

    @Override
    public String answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getString(jsonPath);
    }
}
