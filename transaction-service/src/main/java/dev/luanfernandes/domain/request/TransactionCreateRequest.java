package dev.luanfernandes.domain.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;

@Validated
public record TransactionCreateRequest(
        @NotNull(message = "FromUserId não pode ser nulo") Long fromUserId,
        @NotNull(message = "ToUserId não pode ser nulo") Long toUserId,
        @NotNull(message = "Amount não pode ser nulo")
                @DecimalMin(value = "0.01", inclusive = false, message = "Amount deve ser maior que zero")
                @Positive(message = "Amount deve ser um valor positivo")
                BigDecimal amount) {}
