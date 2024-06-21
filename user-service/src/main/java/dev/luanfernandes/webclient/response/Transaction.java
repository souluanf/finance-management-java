package dev.luanfernandes.webclient.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(Long id, Long fromUserId, Long toUserId, BigDecimal amount, LocalDateTime timestamp) {}
