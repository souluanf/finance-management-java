package dev.luanfernandes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.luanfernandes.config.security.WebSecurityConfig;
import dev.luanfernandes.config.web.ExceptionHandlerAdvice;
import dev.luanfernandes.controller.impl.UserControllerImpl;
import dev.luanfernandes.domain.request.UserCreateRequest;
import dev.luanfernandes.domain.response.UserBalanceResponse;
import dev.luanfernandes.domain.response.UserResponse;
import dev.luanfernandes.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@WebMvcTest
@ContextConfiguration(
        classes = {
            UserControllerImpl.class,
            ExceptionHandlerAdvice.class,
            WebSecurityConfig.class,
        })
@WithMockUser(roles = {"ADMIN"})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateUser() throws Exception {
        UserResponse response = new UserResponse(
                LocalDateTime.now(),
                LocalDateTime.now(),
                "system",
                "system",
                1L,
                "John Doe",
                "john.doe@example.com",
                "123 Main St");

        when(userService.saveUser(any(UserCreateRequest.class))).thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        UserResponse user = new UserResponse(
                LocalDateTime.now(),
                LocalDateTime.now(),
                "system",
                "system",
                1L,
                "John Doe",
                "john.doe@example.com",
                "123 Main St");
        List<UserResponse> users = Collections.singletonList(user);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"));
    }

    @Test
    void testGetUserById() throws Exception {
        UserResponse user = new UserResponse(
                LocalDateTime.now(),
                LocalDateTime.now(),
                "system",
                "system",
                1L,
                "John Doe",
                "john.doe@example.com",
                "123 Main St");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    void testGetUserBalance() throws Exception {
        UserBalanceResponse balanceResponse = new UserBalanceResponse(1L, BigDecimal.valueOf(100.00), 2, 3);

        when(userService.getUserBalance(any(String.class), any(Long.class))).thenReturn(balanceResponse);

        mockMvc.perform(get("/api/v1/users/1/balance").header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.balance").value(100.00))
                .andExpect(jsonPath("$.debitCount").value(2))
                .andExpect(jsonPath("$.creditCount").value(3));
    }
}
