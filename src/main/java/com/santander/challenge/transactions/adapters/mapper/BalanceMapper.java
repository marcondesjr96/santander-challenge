package com.santander.challenge.transactions.adapters.mapper;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class BalanceMapper {

    public static BalanceResponse toBalanceResponse (BigDecimal totalBalance, List<Transaction> transactions){
        return new BalanceResponse(totalBalance, transactions);
    }
}
