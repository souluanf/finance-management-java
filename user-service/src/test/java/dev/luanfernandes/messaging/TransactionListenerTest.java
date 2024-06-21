package dev.luanfernandes.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.luanfernandes.domain.event.TransactionCreatedEvent;
import dev.luanfernandes.domain.response.UserResponse;
import dev.luanfernandes.service.impl.UserServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionListenerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private TransactionListener transactionListener;

    @Test
    void testHandleTransaction() {
        TransactionCreatedEvent event = new TransactionCreatedEvent(1L, 1L, 2L, BigDecimal.valueOf(100.00));
        UserResponse fromUser = new UserResponse(
                LocalDateTime.now(),
                LocalDateTime.now(),
                "admin",
                "admin",
                1L,
                "User1",
                "user1@example.com",
                "Rua Admin, 123");
        UserResponse toUser = new UserResponse(
                LocalDateTime.now(),
                LocalDateTime.now(),
                "admin",
                "admin",
                2L,
                "User1",
                "user1@example.com",
                "Rua Admin, 123");
        when(userService.getUserById(1L)).thenReturn(fromUser);
        when(userService.getUserById(2L)).thenReturn(toUser);

        transactionListener.handleTransaction(event);

        verify(userService, times(1)).getUserById(1L);
        verify(userService, times(1)).getUserById(2L);

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(userService, times(2)).getUserById(captor.capture());

        assertEquals(1L, captor.getAllValues().get(0));
        assertEquals(2L, captor.getAllValues().get(1));
    }
}
