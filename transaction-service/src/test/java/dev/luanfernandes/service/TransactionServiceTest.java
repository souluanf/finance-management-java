package dev.luanfernandes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.luanfernandes.domain.entity.Transaction;
import dev.luanfernandes.domain.enums.TransactionType;
import dev.luanfernandes.domain.event.TransactionCreatedEvent;
import dev.luanfernandes.domain.mapper.TransactionMapper;
import dev.luanfernandes.domain.request.TransactionCreateRequest;
import dev.luanfernandes.domain.response.TransactionResponse;
import dev.luanfernandes.messaging.TransactionEventPublisher;
import dev.luanfernandes.repository.TransactionRepository;
import dev.luanfernandes.service.impl.TransactionServiceImpl;
import dev.luanfernandes.webclient.UserWebClient;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserWebClient userWebClient;

    @Mock
    private TransactionEventPublisher transactionEventPublisher;

    @Spy
    private TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransactionCreateRequest createRequest;
    private Transaction transaction;
    private TransactionResponse transactionResponse;

    @BeforeEach
    void setUp() {
        createRequest = new TransactionCreateRequest(1L, 2L, BigDecimal.valueOf(100.00));
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setFromUserId(1L);
        transaction.setToUserId(2L);
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transactionResponse = transactionMapper.map(transaction);
    }

    @Test
    void saveTransaction_ShouldSaveTransactionAndPublishEvent() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponse response = transactionService.saveTransaction("token", createRequest);

        verify(userWebClient, times(1)).getUserById("token", 1L);
        verify(userWebClient, times(1)).getUserById("token", 2L);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(transactionEventPublisher, times(1)).publishTransactionCreatedEvent(any(TransactionCreatedEvent.class));

        assertEquals(transactionResponse, response);
    }

    @Test
    void getTransactionById_ShouldReturnTransaction() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        TransactionResponse response = transactionService.getTransactionById(1L);

        verify(transactionRepository, times(1)).findById(1L);
        assertEquals(transactionResponse, response);
    }

    @Test
    void getTransactionById_ShouldThrowEntityNotFoundException() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> transactionService.getTransactionById(1L));
    }

    @Test
    void getAllTransactions_ShouldReturnAllTransactions() {
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<TransactionResponse> responses = transactionService.getAllTransactions();

        verify(transactionRepository, times(1)).findAll();
        assertEquals(1, responses.size());
        assertEquals(transactionResponse, responses.get(0));
    }

    @Test
    void getTransactionsByUserId_ShouldReturnAllTransactions() {
        when(userWebClient.getUserById(anyString(), anyLong())).thenReturn(null);
        when(transactionRepository.findByFromUserIdOrToUserId(anyLong(), anyLong()))
                .thenReturn(List.of(transaction));

        List<TransactionResponse> responses = transactionService.getTransactionsByUserId("token", 1L, null);

        verify(userWebClient, times(1)).getUserById("token", 1L);
        verify(transactionRepository, times(1)).findByFromUserIdOrToUserId(1L, 1L);
        assertEquals(1, responses.size());
        assertEquals(transactionResponse, responses.get(0));
    }

    @Test
    void getTransactionsByUserId_ShouldReturnDebitTransactions() {
        when(userWebClient.getUserById(anyString(), anyLong())).thenReturn(null);
        when(transactionRepository.findByFromUserId(anyLong())).thenReturn(List.of(transaction));

        List<TransactionResponse> responses =
                transactionService.getTransactionsByUserId("token", 1L, TransactionType.DEBIT);

        verify(userWebClient, times(1)).getUserById("token", 1L);
        verify(transactionRepository, times(1)).findByFromUserId(1L);
        assertEquals(1, responses.size());
        assertEquals(transactionResponse, responses.get(0));
    }

    @Test
    void getTransactionsByUserId_ShouldReturnCreditTransactions() {
        when(userWebClient.getUserById(anyString(), anyLong())).thenReturn(null);
        when(transactionRepository.findByToUserId(anyLong())).thenReturn(List.of(transaction));

        List<TransactionResponse> responses =
                transactionService.getTransactionsByUserId("token", 1L, TransactionType.CREDIT);

        verify(userWebClient, times(1)).getUserById("token", 1L);
        verify(transactionRepository, times(1)).findByToUserId(1L);
        assertEquals(1, responses.size());
        assertEquals(transactionResponse, responses.get(0));
    }
}
