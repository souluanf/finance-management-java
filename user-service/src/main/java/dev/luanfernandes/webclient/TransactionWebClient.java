package dev.luanfernandes.webclient;

import static org.springframework.http.HttpMethod.GET;

import dev.luanfernandes.webclient.response.Transaction;
import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TransactionWebClient {

    private final RestTemplate restTemplate;

    @Value("${api.transaction.host}")
    private String transactionServiceUrl;

    public List<Transaction> getTransactionsByUserId(String token, Long userId) {
        String url = transactionServiceUrl + "/api/v1/transactions/user/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Transaction[]> response;
        try {
            response = restTemplate.exchange(url, GET, entity, Transaction[].class);
        } catch (RestClientException e) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
