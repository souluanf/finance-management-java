package dev.luanfernandes.service.impl;

import dev.luanfernandes.domain.entity.Transaction;
import dev.luanfernandes.domain.enums.TransactionType;
import dev.luanfernandes.domain.event.TransactionCreatedEvent;
import dev.luanfernandes.domain.mapper.TransactionMapper;
import dev.luanfernandes.domain.request.TransactionCreateRequest;
import dev.luanfernandes.domain.response.TransactionResponse;
import dev.luanfernandes.messaging.TransactionEventPublisher;
import dev.luanfernandes.repository.TransactionRepository;
import dev.luanfernandes.service.TransactionService;
import dev.luanfernandes.webclient.UserWebClient;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionEventPublisher transactionEventPublisher;
    private final UserWebClient userWebClient;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public TransactionResponse saveTransaction(String token, TransactionCreateRequest aTransaction) {
        userWebClient.getUserById(token, aTransaction.fromUserId());
        userWebClient.getUserById(token, aTransaction.toUserId());
        Transaction saved = transactionRepository.save(transactionMapper.map(aTransaction));
        TransactionCreatedEvent event = TransactionCreatedEvent.builder()
                .id(saved.getId())
                .fromUserId(saved.getFromUserId())
                .toUserId(saved.getToUserId())
                .amount(saved.getAmount())
                .build();

        transactionEventPublisher.publishTransactionCreatedEvent(event);
        return transactionMapper.map(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(Long anId) {
        Transaction transaction = transactionRepository
                .findById(anId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + anId));

        return transactionMapper.map(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::map)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByUserId(String token, Long userId, TransactionType aType) {
        userWebClient.getUserById(token, userId);
        if (aType == null) return getAllTransactionsByUserId(userId);
        return switch (aType) {
            case DEBIT -> getDebitTransactionsByUserId(userId);
            case CREDIT -> getCreditTransactionsByUserId(userId);
        };
    }

    private List<TransactionResponse> getAllTransactionsByUserId(Long userId) {
        return transactionRepository.findByFromUserIdOrToUserId(userId, userId).stream()
                .map(transactionMapper::map)
                .toList();
    }

    private List<TransactionResponse> getDebitTransactionsByUserId(Long userId) {
        return transactionRepository.findByFromUserId(userId).stream()
                .map(transactionMapper::map)
                .toList();
    }

    private List<TransactionResponse> getCreditTransactionsByUserId(Long userId) {
        return transactionRepository.findByToUserId(userId).stream()
                .map(transactionMapper::map)
                .toList();
    }
}
