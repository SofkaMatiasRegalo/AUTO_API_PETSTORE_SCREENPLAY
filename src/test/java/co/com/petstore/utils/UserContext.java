package co.com.petstore.utils;

import co.com.petstore.models.User;
import net.serenitybdd.screenplay.Actor;

public class UserContext {
    private static final ThreadLocal<UserContext> CURRENT = ThreadLocal.withInitial(UserContext::new);

    public Actor actor;
    public User usuario;

    public static UserContext current() {
        return CURRENT.get();
    }

    public static void clear() {
        CURRENT.remove();
    }
}
