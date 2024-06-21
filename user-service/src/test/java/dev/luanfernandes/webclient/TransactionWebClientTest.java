package dev.luanfernandes.webclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import dev.luanfernandes.webclient.response.Transaction;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class TransactionWebClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TransactionWebClient transactionWebClient;

    private static final String TRANSACTION_SERVICE_URL = "http://localhost:8082/transaction-service";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(transactionWebClient, "transactionServiceUrl", TRANSACTION_SERVICE_URL);
    }

    @Test
    void shouldReturnTransactionsWhenUserHasTransactions() {
        Long userId = 1L;
        String token = "Bearer valid_token";
        Transaction[] expectedTransactions = {
            new Transaction(1L, userId, 2L, BigDecimal.TEN, LocalDateTime.now()),
            new Transaction(2L, userId, 3L, BigDecimal.ONE, LocalDateTime.now())
        };
        ResponseEntity<Transaction[]> responseEntity = new ResponseEntity<>(expectedTransactions, HttpStatus.OK);
        when(restTemplate.exchange(
                        eq(TRANSACTION_SERVICE_URL + "/api/v1/transactions/user/" + userId),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(Transaction[].class)))
                .thenReturn(responseEntity);

        List<Transaction> actualTransactions = transactionWebClient.getTransactionsByUserId(token, userId);

        assertEquals(List.of(expectedTransactions), actualTransactions);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUserHasNoTransactions() {
        Long userId = 1L;
        String token = "Bearer valid_token";
        when(restTemplate.exchange(
                        eq(TRANSACTION_SERVICE_URL + "/api/v1/transactions/user/" + userId),
                        eq(HttpMethod.GET),
                        any(HttpEntity.class),
                        eq(Transaction[].class)))
                .thenThrow(new RestClientException("User with id " + userId + " not found"));

        assertThrows(EntityNotFoundException.class, () -> transactionWebClient.getTransactionsByUserId(token, userId));
    }
}
