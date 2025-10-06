package com.santander.challenge.transactions.adapters.dto;

import java.math.BigDecimal;

public record PayRequest(
        BigDecimal amount
) {
}