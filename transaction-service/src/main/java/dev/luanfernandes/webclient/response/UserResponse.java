package dev.luanfernandes.webclient.response;

import java.time.LocalDateTime;

public record UserResponse(
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String lastModifiedBy,
        Long id,
        String name,
        String email,
        String address) {}
