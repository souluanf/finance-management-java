package dev.luanfernandes.webclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import dev.luanfernandes.webclient.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class UserWebClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserWebClient userWebClient;

    private static final String USER_SERVICE_URL = "http://localhost:8081/user-service";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userWebClient, "userServiceUrl", USER_SERVICE_URL);
    }

    @Test
    void shouldReturnUserResponseWhenUserExists() {
        Long userId = 1L;
        String token = "Bearer valid_token";
        UserResponse expectedUserResponse = new UserResponse(
                LocalDateTime.now(),
                LocalDateTime.now(),
                "admin",
                "admin",
                userId,
                "John Doe",
                "johndoe@example.com",
                "123 Main St");
        ResponseEntity<UserResponse> responseEntity = new ResponseEntity<>(expectedUserResponse, HttpStatus.OK);
        when(restTemplate.exchange(
                        eq(USER_SERVICE_URL + "/api/v1/users/" + userId),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(UserResponse.class)))
                .thenReturn(responseEntity);
        UserResponse actualUserResponse = userWebClient.getUserById(token, userId);
        assertEquals(expectedUserResponse, actualUserResponse);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUserDoesNotExist() {
        Long userId = 1L;
        String token = "Bearer valid_token";
        when(restTemplate.exchange(
                        eq(USER_SERVICE_URL + "/api/v1/users/" + userId),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(UserResponse.class)))
                .thenThrow(new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "User with id " + userId + " not found",
                        null,
                        null,
                        StandardCharsets.UTF_8));

        assertThrows(EntityNotFoundException.class, () -> userWebClient.getUserById(token, userId));
    }

    @Test
    void shouldThrowHttpClientErrorExceptionWhenCallFails() {
        Long userId = 1L;
        String token = "Bearer valid_token";
        when(restTemplate.exchange(
                        eq(USER_SERVICE_URL + "/api/v1/users/" + userId),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(UserResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(HttpClientErrorException.class, () -> userWebClient.getUserById(token, userId));
    }
}
