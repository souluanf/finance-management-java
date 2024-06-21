package dev.luanfernandes.controller;

import static dev.luanfernandes.constants.PathConstants.TRANSACTIONS_ID;
import static dev.luanfernandes.constants.PathConstants.TRANSACTIONS_USER_ID;
import static dev.luanfernandes.constants.PathConstants.TRANSACTIONS_V1;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import dev.luanfernandes.domain.enums.TransactionType;
import dev.luanfernandes.domain.request.TransactionCreateRequest;
import dev.luanfernandes.domain.response.TransactionResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(produces = APPLICATION_JSON_VALUE)
public interface TransactionController {
    @PostMapping(TRANSACTIONS_V1)
    ResponseEntity<TransactionResponse> createTransaction(
            @RequestHeader(value = AUTHORIZATION) String token,
            @Valid @RequestBody TransactionCreateRequest aTransaction);

    @GetMapping(TRANSACTIONS_ID)
    ResponseEntity<TransactionResponse> getTransactionsById(@PathVariable Long anId);

    @GetMapping(TRANSACTIONS_V1)
    ResponseEntity<List<TransactionResponse>> getAllTransactions();

    @GetMapping(TRANSACTIONS_USER_ID)
    ResponseEntity<List<TransactionResponse>> getTransactionsByUserId(
            @RequestHeader(value = AUTHORIZATION) String token,
            @PathVariable Long anId,
            @RequestParam(value = "type", required = false) TransactionType aType);
}
