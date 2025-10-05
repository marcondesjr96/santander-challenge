package com.santander.challenge.transactions.adapters.dto;

import com.santander.challenge.transactions.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record BalanceResponse(
        BigDecimal totalBalance,
        List<Transaction> transactions
) {
}
