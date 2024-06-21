package dev.luanfernandes.domain.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String lastModifiedBy,
        Long id,
        Long fromUserId,
        Long toUserId,
        BigDecimal amount) {}
