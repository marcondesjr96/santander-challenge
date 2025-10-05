package com.santander.challenge.transactions.adapters.mapper;

import com.santander.challenge.transactions.adapters.dto.TransactionResponse;
import com.santander.challenge.transactions.domain.model.Transaction;

public class TransactionMapper {

    public static TransactionResponse toTransactionRepsponse(Transaction transaction){
        return new TransactionResponse(transaction.getType(), transaction.getAmount(), transaction.getOccurredAt());
    }
}
