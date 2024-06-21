package dev.luanfernandes.domain.event;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record TransactionCreatedEvent(Long id, Long fromUserId, Long toUserId, BigDecimal amount) {}
