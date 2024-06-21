package dev.luanfernandes.webclient;

import static dev.luanfernandes.constants.PathConstants.AUTH_TOKEN;
import static dev.luanfernandes.domain.request.TokenRequestBuilder.getTokenRequest;
import static dev.luanfernandes.domain.response.TokenResponseBuilder.getTokenResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import dev.luanfernandes.domain.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class KeycloakClientTest {

    @InjectMocks
    private KeycloakClient keycloakClient;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getTokenShouldReturnTokenResponse() {
        var tokenRequest = getTokenRequest();
        var expectedResponse = getTokenResponse();
        when(restTemplate.postForObject(eq(AUTH_TOKEN), any(HttpEntity.class), eq(TokenResponse.class)))
                .thenReturn(expectedResponse);
        setField(keycloakClient, "tokenUri", AUTH_TOKEN);
        var actualResponse = keycloakClient.getToken(tokenRequest);
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.accessToken(), actualResponse.accessToken());
        verify(restTemplate).postForObject(eq(AUTH_TOKEN), any(HttpEntity.class), eq(TokenResponse.class));
    }
}
