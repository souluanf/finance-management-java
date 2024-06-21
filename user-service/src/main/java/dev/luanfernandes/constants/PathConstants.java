package dev.luanfernandes.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathConstants {
    private static final String API = "/api";

    public static final String AUTH_V1 = API + "/v1/auth";
    public static final String AUTH_TOKEN = AUTH_V1 + "/token";

    public static final String USERS_V1 = API + "/v1/users";
    public static final String USERS_BY_ID = USERS_V1 + "/{anId}";
    public static final String USERS_BALANCE = USERS_BY_ID + "/balance";
}
