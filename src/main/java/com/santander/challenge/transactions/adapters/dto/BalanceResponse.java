package com.santander.challenge.transactions.adapters.dto;

import java.math.BigDecimal;
import java.util.List;

public record BalanceResponse(
        BigDecimal totalBalance,
        List<TransactionResponse> transactions
) {
}
