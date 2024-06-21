package dev.luanfernandes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.luanfernandes.domain.entity.User;
import dev.luanfernandes.domain.mapper.UserMapper;
import dev.luanfernandes.domain.request.UserCreateRequest;
import dev.luanfernandes.domain.response.UserBalanceResponse;
import dev.luanfernandes.domain.response.UserResponse;
import dev.luanfernandes.repository.UserRepository;
import dev.luanfernandes.service.impl.UserServiceImpl;
import dev.luanfernandes.webclient.TransactionWebClient;
import dev.luanfernandes.webclient.response.Transaction;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionWebClient transactionWebClient;

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSaveUser() {
        UserCreateRequest request = new UserCreateRequest("John", "john.doe@example.com", "Rua teste");
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john.doe@example.com");
        user.setAddress("Rua teste");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.saveUser(request);

        assertNotNull(response);
        assertEquals("John", response.name());
        assertEquals("john.doe@example.com", response.email());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John");
        user1.setEmail("john.doe@example.com");
        user1.setAddress("Rua teste");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane");
        user2.setEmail("jane.doe@example.com");
        user2.setAddress("Rua teste");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserResponse> responses = userService.getAllUsers();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john.doe@example.com");
        user.setAddress("Rua teste");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(userId);

        assertNotNull(response);
        assertEquals("John", response.name());
        assertEquals("john.doe@example.com", response.email());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_NotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserBalance() {
        Long userId = 1L;
        String token = "Bearer token";
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john.doe@example.com");
        user.setAddress("Rua teste");
        Transaction transaction1 = new Transaction(1L, userId, 2L, BigDecimal.valueOf(100.00), LocalDateTime.now());
        Transaction transaction2 = new Transaction(2L, 2L, userId, BigDecimal.valueOf(50.00), LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionWebClient.getTransactionsByUserId(token, userId))
                .thenReturn(List.of(transaction1, transaction2));

        UserBalanceResponse response = userService.getUserBalance(token, userId);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(-50.00), response.balance());
        assertEquals(1, response.debitCount());
        assertEquals(1, response.creditCount());
        verify(userRepository, times(1)).findById(userId);
        verify(transactionWebClient, times(1)).getTransactionsByUserId(token, userId);
    }
}
