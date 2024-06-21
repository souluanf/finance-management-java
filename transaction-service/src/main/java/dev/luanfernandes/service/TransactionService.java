package dev.luanfernandes.service;

import dev.luanfernandes.domain.enums.TransactionType;
import dev.luanfernandes.domain.request.TransactionCreateRequest;
import dev.luanfernandes.domain.response.TransactionResponse;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {
    TransactionResponse saveTransaction(String token, TransactionCreateRequest aTransaction);

    @Transactional(readOnly = true)
    TransactionResponse getTransactionById(Long anId);

    List<TransactionResponse> getAllTransactions();

    List<TransactionResponse> getTransactionsByUserId(String token, Long userId, TransactionType aType);
}
