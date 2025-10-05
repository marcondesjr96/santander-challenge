package com.santander.challenge.transactions.adapters.mapper;

import com.santander.challenge.transactions.adapters.dto.TransactionResponse;
import com.santander.challenge.transactions.domain.model.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionMapper {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static TransactionResponse toTransactionRepsponse(Transaction transaction){
        String formattedDate = transaction.getOccurredAt().format(FORMATTER);
        return new TransactionResponse(
                transaction.getType(),
                transaction.getAmount(),
                formattedDate
        );
    }
    public static List<TransactionResponse> toTransactionResponseList (List<Transaction> transactionList){
        return transactionList
                .stream()
                .map(TransactionMapper::toTransactionRepsponse)
                .toList();
    }
}
