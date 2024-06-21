package dev.luanfernandes.controller.impl;

import dev.luanfernandes.controller.UserController;
import dev.luanfernandes.domain.request.UserCreateRequest;
import dev.luanfernandes.domain.response.UserBalanceResponse;
import dev.luanfernandes.domain.response.UserResponse;
import dev.luanfernandes.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> createUser(UserCreateRequest anUserCreateRequest) {
        return ResponseEntity.ok(userService.saveUser(anUserCreateRequest));
    }

    @Override
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long anId) {
        return ResponseEntity.ok(userService.getUserById(anId));
    }

    @Override
    public ResponseEntity<UserBalanceResponse> getUserBalance(String token, Long anId) {
        return ResponseEntity.ok(userService.getUserBalance(token, anId));
    }
}
