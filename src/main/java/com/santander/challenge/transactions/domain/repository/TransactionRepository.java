package com.santander.challenge.transactions.domain.repository;

import com.santander.challenge.transactions.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findByAccountId(UUID accountId);
}
