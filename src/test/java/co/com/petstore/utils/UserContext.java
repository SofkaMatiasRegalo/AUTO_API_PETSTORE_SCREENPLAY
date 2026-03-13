package co.com.petstore.utils;

import co.com.petstore.models.User;
import net.serenitybdd.screenplay.Actor;

public class UserContext {
    private static final ThreadLocal<UserContext> CURRENT = ThreadLocal.withInitial(UserContext::new);

    private Actor actor;
    private User usuario;

    public static UserContext current() {
        return CURRENT.get();
    }

    public static void clear() {
        CURRENT.remove();
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
}
