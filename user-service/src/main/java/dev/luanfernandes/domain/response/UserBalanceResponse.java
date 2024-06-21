package dev.luanfernandes.domain.response;

import java.math.BigDecimal;

public record UserBalanceResponse(Long userId, BigDecimal balance, Integer debitCount, Integer creditCount) {}
