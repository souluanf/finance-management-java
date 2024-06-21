package dev.luanfernandes.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record UserCreateRequest(
        @NotBlank(message = "Name is required")
                @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
                String name,
        @NotBlank(message = "Email is required")
                @Email(message = "Email is not valid", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
                String email,
        @NotBlank(message = "Address is required")
                @Size(min = 3, max = 100, message = "Address must be between 3 and 100 characters")
                String address) {}
