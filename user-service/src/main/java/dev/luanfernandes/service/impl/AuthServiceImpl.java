package dev.luanfernandes.service.impl;

import dev.luanfernandes.domain.request.TokenRequest;
import dev.luanfernandes.domain.response.TokenResponse;
import dev.luanfernandes.service.AuthService;
import dev.luanfernandes.webclient.KeycloakClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KeycloakClient webClient;

    @Override
    public TokenResponse getToken(TokenRequest tokenRequest) {
        return webClient.getToken(tokenRequest);
    }
}
