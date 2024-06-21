package dev.luanfernandes.domain.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import dev.luanfernandes.domain.entity.Transaction;
import dev.luanfernandes.domain.request.TransactionCreateRequest;
import dev.luanfernandes.domain.response.TransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface TransactionMapper {
    TransactionResponse map(Transaction transaction);

    Transaction map(TransactionCreateRequest transactionCreateRequest);
}
