package com.santander.challenge.transactions.domain.model;

import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public class Transaction {
    private UUID id;
    private UUID accountId;
    private TransactionTypeEnum type;
    private BigDecimal amount;
    private LocalDateTime occurredAt;

    public Transaction(UUID id, UUID accountId, TransactionTypeEnum type, BigDecimal amount, LocalDateTime occurredAt) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.occurredAt = occurredAt;
    }

    public Transaction(UUID accountId, TransactionTypeEnum type, BigDecimal amount, LocalDateTime occurredAt) {
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
