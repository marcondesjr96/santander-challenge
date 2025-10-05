package com.santander.challenge.transactions.adapters.dto;

import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;

import java.math.BigDecimal;

public record TransactionResponse(
        TransactionTypeEnum type, BigDecimal amount, String occurredAt
) {
}
