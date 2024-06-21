package dev.luanfernandes.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenApiConfigConstants {
    public static final String OPENAPI_INFO_TITLE = "Transactions API";
    public static final String OPENAPI_INFO_VERSION = "0.0.1";
    public static final String OPENAPI_INFO_DESCRIPTION = "API for transactions";
    public static final String OPENAPI_CONTACT_NAME = "Luan Fernandes";
    public static final String OPENAPI_CONTACT_EMAIL = "contact@luanfernandes.dev";
    public static final String OPENAPI_CONTACT_URL = "https://luanfernandes.dev";
    public static final String OPENAPI_EXTERNAL_DOCS_DESCRIPTION = "Git repository";
    public static final String OPENAPI_EXTERNAL_DOCS_URL =
            "https://github.com/souluanf/finance-management-java/transaction-service";

    public static final String OPENAPI_SERVERS_URLS = "${openapi-servers-urls}";
}
