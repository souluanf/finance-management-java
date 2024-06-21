package dev.luanfernandes.domain.event;

import java.math.BigDecimal;

public record TransactionCreatedEvent(Long id, Long fromUserId, Long toUserId, BigDecimal amount) {}
