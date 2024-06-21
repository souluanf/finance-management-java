package dev.luanfernandes.webclient;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import dev.luanfernandes.webclient.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserWebClient {

    private final RestTemplate restTemplate;

    @Value("${api.user.host}")
    private String userServiceUrl;

    public UserResponse getUserById(String token, Long userId) {
        String url = userServiceUrl + "/api/v1/users/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserResponse> response;
        try {
            response = restTemplate.exchange(url, GET, entity, UserResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == NOT_FOUND.value()) {
                throw new EntityNotFoundException("User with id " + userId + " not found");
            }
            throw e;
        }
        return response.getBody();
    }
}
