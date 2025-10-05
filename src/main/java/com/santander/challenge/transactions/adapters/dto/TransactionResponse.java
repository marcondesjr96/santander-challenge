package com.santander.challenge.transactions.adapters.dto;

import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        TransactionTypeEnum type, BigDecimal amount, LocalDateTime occurredAt
) {
}
