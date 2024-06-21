package dev.luanfernandes.service;

import dev.luanfernandes.domain.request.UserCreateRequest;
import dev.luanfernandes.domain.response.UserBalanceResponse;
import dev.luanfernandes.domain.response.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse saveUser(UserCreateRequest anUserCreateRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long anId);

    UserBalanceResponse getUserBalance(String token, Long anId);
}
