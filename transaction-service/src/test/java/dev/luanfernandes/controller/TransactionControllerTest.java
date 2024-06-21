package dev.luanfernandes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.luanfernandes.config.security.WebSecurityConfig;
import dev.luanfernandes.config.web.ExceptionHandlerAdvice;
import dev.luanfernandes.controller.impl.TransactionControllerImpl;
import dev.luanfernandes.domain.enums.TransactionType;
import dev.luanfernandes.domain.request.TransactionCreateRequest;
import dev.luanfernandes.domain.response.TransactionResponse;
import dev.luanfernandes.service.TransactionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@WebMvcTest
@ContextConfiguration(
        classes = {
            TransactionControllerImpl.class,
            ExceptionHandlerAdvice.class,
            WebSecurityConfig.class,
        })
@WithMockUser(roles = {"ADMIN"})
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createTransaction_ShouldReturnTransactionResponse() throws Exception {
        TransactionCreateRequest request = new TransactionCreateRequest(1L, 2L, BigDecimal.valueOf(100.00));
        TransactionResponse response = new TransactionResponse(
                LocalDateTime.now(), LocalDateTime.now(), "admin", "admin", 1L, 1L, 2L, BigDecimal.valueOf(100.00));

        when(transactionService.saveTransaction(any(), eq(request))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transactions")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromUserId\":1,\"toUserId\":2,\"amount\":100.00}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getTransactionsById_ShouldReturnTransactionResponse() throws Exception {
        TransactionResponse response = new TransactionResponse(
                LocalDateTime.now(), LocalDateTime.now(), "admin", "admin", 1L, 1L, 2L, BigDecimal.valueOf(100.00));

        when(transactionService.getTransactionById(anyLong())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"fromUserId\":1,\"toUserId\":2,\"amount\":100.00}"));
    }

    @Test
    void getAllTransactions_ShouldReturnTransactionResponseList() throws Exception {
        TransactionResponse response = new TransactionResponse(
                LocalDateTime.now(), LocalDateTime.now(), "admin", "admin", 1L, 1L, 2L, BigDecimal.valueOf(100.00));
        List<TransactionResponse> responseList = List.of(response);

        when(transactionService.getAllTransactions()).thenReturn(responseList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"fromUserId\":1,\"toUserId\":2,\"amount\":100.00}]"));
    }

    @Test
    void getCreditTransactionsByUserId_ShouldReturnTransactionResponseList() throws Exception {
        TransactionResponse response = new TransactionResponse(
                LocalDateTime.now(), LocalDateTime.now(), "admin", "admin", 1L, 1L, 2L, BigDecimal.valueOf(100.00));
        List<TransactionResponse> responseList = List.of(response);

        when(transactionService.getTransactionsByUserId(any(), anyLong(), any(TransactionType.class)))
                .thenReturn(responseList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/user/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());
    }
}
