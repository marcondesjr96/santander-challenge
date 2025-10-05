package com.santander.challenge.transactions.infrastructure.persistence.mapper;

import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;
import com.santander.challenge.transactions.infrastructure.persistence.entity.AccountEntity;
import com.santander.challenge.transactions.infrastructure.persistence.entity.TransactionEntity;

import java.util.UUID;

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

    public static TransactionEntity toEntity(Transaction domain, AccountEntity accountEntity) {
        if (domain == null) return null;
        TransactionEntity entity = new TransactionEntity();
        entity.setId(domain.getId() != null ? domain.getId() : UUID.randomUUID());
        entity.setAccount(accountEntity);
        entity.setAmount(domain.getAmount());
        entity.setOccurredAt(domain.getOccurredAt());
        entity.setType(TransactionTypeEnum.getByType(domain.getType().getType()));
        return entity;
    }
}
