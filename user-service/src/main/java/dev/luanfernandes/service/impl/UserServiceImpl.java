package dev.luanfernandes.service.impl;

import static java.lang.String.format;

import dev.luanfernandes.domain.entity.User;
import dev.luanfernandes.domain.mapper.UserMapper;
import dev.luanfernandes.domain.request.UserCreateRequest;
import dev.luanfernandes.domain.response.UserBalanceResponse;
import dev.luanfernandes.domain.response.UserResponse;
import dev.luanfernandes.repository.UserRepository;
import dev.luanfernandes.service.UserService;
import dev.luanfernandes.webclient.TransactionWebClient;
import dev.luanfernandes.webclient.response.Transaction;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TransactionWebClient transactionWebClient;
    private final UserMapper userMapper;

    @Override
    public UserResponse saveUser(UserCreateRequest anUserCreateRequest) {
        User user = userRepository.save(userMapper.map(anUserCreateRequest));
        return userMapper.map(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::map).toList();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(format("User with id %d not found", userId)));
        return userMapper.map(user);
    }

    @Override
    public UserBalanceResponse getUserBalance(String token, Long userId) {
        getUserById(userId);
        List<Transaction> transactions = transactionWebClient.getTransactionsByUserId(token, userId);
        BigDecimal balance = BigDecimal.ZERO;
        int debitCount = 0;
        int creditCount = 0;
        for (Transaction transaction : transactions) {
            if (transaction.fromUserId().equals(userId)) {
                balance = balance.subtract(transaction.amount());
                debitCount++;
            } else {
                balance = balance.add(transaction.amount());
                creditCount++;
            }
        }
        return new UserBalanceResponse(userId, balance, debitCount, creditCount);
    }
}
