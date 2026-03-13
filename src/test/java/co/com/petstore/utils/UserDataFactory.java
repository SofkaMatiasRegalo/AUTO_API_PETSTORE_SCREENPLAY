package co.com.petstore.utils;

import co.com.petstore.models.User;

public final class UserDataFactory {

    private UserDataFactory() {
    }

    public static User newUser(String username) {
        return User.builder()
            .id(Math.floorMod(username.hashCode(), 900000) + 100000)
            .username(username)
            .firstName(Constantes.USER_FIRST_NAME)
            .lastName(Constantes.USER_LAST_NAME)
            .email(Constantes.USER_EMAIL)
            .password(Constantes.USER_PASSWORD)
            .phone(Constantes.USER_PHONE)
            .userStatus(Constantes.USER_STATUS_ACTIVE)
            .build();
    }

    public static User updatedUserFrom(User baseUser, String username) {
        return User.builder()
            .id(baseUser.getId())
            .username(username)
            .firstName(Constantes.USER_UPDATED_FIRST_NAME)
            .lastName(Constantes.USER_LAST_NAME)
            .email(Constantes.USER_UPDATED_EMAIL)
            .password(baseUser.getPassword())
            .phone(Constantes.USER_UPDATED_PHONE)
            .userStatus(Constantes.USER_STATUS_ACTIVE)
            .build();
    }
}
