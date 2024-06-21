package dev.luanfernandes.service;

import static dev.luanfernandes.domain.request.TokenRequestBuilder.getTokenRequest;
import static dev.luanfernandes.domain.response.TokenResponseBuilder.getTokenResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.luanfernandes.domain.request.TokenRequest;
import dev.luanfernandes.domain.response.TokenResponse;
import dev.luanfernandes.service.impl.AuthServiceImpl;
import dev.luanfernandes.webclient.KeycloakClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private KeycloakClient keycloakClient;

    @Test
    void getTokenShouldCallKeycloakClientAndReturnResponse() {
        var tokenRequest = getTokenRequest();
        var expectedResponse = getTokenResponse();
        when(keycloakClient.getToken(any(TokenRequest.class))).thenReturn(expectedResponse);
        TokenResponse actualResponse = authService.getToken(tokenRequest);
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.accessToken(), actualResponse.accessToken());
        verify(keycloakClient).getToken(any(TokenRequest.class));
    }
}
