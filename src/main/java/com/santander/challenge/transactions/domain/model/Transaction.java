package com.santander.challenge.transactions.domain.model;

import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public class Transaction {
    private final UUID id;
    private final UUID accountId;
    private final TransactionTypeEnum type;
    private final BigDecimal amount;
    private final LocalDateTime occurredAt;

    public Transaction(UUID id, UUID accountId, TransactionTypeEnum type, BigDecimal amount, LocalDateTime occurredAt) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.occurredAt = occurredAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public TransactionTypeEnum getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
