package com.santander.challenge.transactions.infrastructure.persistence.mapper;

import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;
import com.santander.challenge.transactions.infrastructure.persistence.entity.AccountEntity;
import com.santander.challenge.transactions.infrastructure.persistence.entity.TransactionEntity;

public class TransactionMapper {

    public static Transaction toDomain(TransactionEntity entity) {
        if (entity == null) return null;
        return new Transaction(
                entity.getId(),
                entity.getAccount().getId(),
                TransactionTypeEnum.getByType(entity.getType().getType()),
                entity.getAmount(),
                entity.getOccurredAt()
        );
    }

    public static TransactionEntity toEntity(Transaction transaction, AccountEntity accountEntity) {
        if (transaction == null) return null;
        return TransactionEntity
                .builder()
                .account(accountEntity)
                .amount(transaction.getAmount())
                .occurredAt(transaction.getOccurredAt())
                .type(TransactionTypeEnum.getByType(transaction.getType().getType()))
                .build();
    }
}
