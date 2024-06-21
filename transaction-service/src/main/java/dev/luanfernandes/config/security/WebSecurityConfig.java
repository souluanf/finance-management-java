package dev.luanfernandes.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static dev.luanfernandes.constants.PathConstants.AUTH_TOKEN;
import static dev.luanfernandes.constants.PathConstants.TRANSACTIONS_ID;
import static dev.luanfernandes.constants.PathConstants.TRANSACTIONS_USER_ID;
import static dev.luanfernandes.constants.PathConstants.TRANSACTIONS_V1;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    private static final String ADMIN_ROLE = "ADMIN";

    private static final String[] ALLOWED_PATHS = {
        "/actuator/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", AUTH_TOKEN, "/"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth.requestMatchers(ALLOWED_PATHS)
                        .permitAll()
                        .requestMatchers(POST, TRANSACTIONS_V1)
                        .hasRole(ADMIN_ROLE)
                        .requestMatchers(GET, TRANSACTIONS_V1)
                        .hasRole(ADMIN_ROLE)
                        .requestMatchers(GET, TRANSACTIONS_ID)
                        .hasRole(ADMIN_ROLE)
                        .requestMatchers(GET, TRANSACTIONS_USER_ID)
                        .hasRole(ADMIN_ROLE)
                        .anyRequest()
                        .denyAll())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtConverter())))
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
