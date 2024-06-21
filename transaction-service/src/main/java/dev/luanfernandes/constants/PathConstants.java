package dev.luanfernandes.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathConstants {
    private static final String API = "/api";

    public static final String AUTH_V1 = API + "/v1/auth";
    public static final String AUTH_TOKEN = AUTH_V1 + "/token";

    public static final String TRANSACTIONS_V1 = API + "/v1/transactions";
    public static final String TRANSACTIONS_ID = TRANSACTIONS_V1 + "/{anId}";
    public static final String TRANSACTIONS_USER_ID = TRANSACTIONS_V1 + "/user/{anId}";
}
