package dev.luanfernandes.controller.impl;

import static org.springframework.http.HttpStatus.CREATED;

import dev.luanfernandes.controller.TransactionController;
import dev.luanfernandes.domain.enums.TransactionType;
import dev.luanfernandes.domain.request.TransactionCreateRequest;
import dev.luanfernandes.domain.response.TransactionResponse;
import dev.luanfernandes.service.TransactionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {

    private final TransactionService transactionService;

    @Override
    public ResponseEntity<TransactionResponse> createTransaction(String token, TransactionCreateRequest aTransaction) {
        return ResponseEntity.status(CREATED).body(transactionService.saveTransaction(token, aTransaction));
    }

    @Override
    public ResponseEntity<TransactionResponse> getTransactionsById(Long anId) {
        return ResponseEntity.ok(transactionService.getTransactionById(anId));
    }

    @Override
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @Override
    public ResponseEntity<List<TransactionResponse>> getTransactionsByUserId(
            String token, Long anId, TransactionType aType) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(token, anId, aType));
    }
}
