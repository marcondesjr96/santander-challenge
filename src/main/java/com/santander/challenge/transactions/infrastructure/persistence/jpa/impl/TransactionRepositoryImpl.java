package com.santander.challenge.transactions.infrastructure.persistence.jpa.impl;

import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.repository.TransactionRepository;
import com.santander.challenge.transactions.infrastructure.persistence.entity.AccountEntity;
import com.santander.challenge.transactions.infrastructure.persistence.entity.TransactionEntity;
import com.santander.challenge.transactions.infrastructure.persistence.jpa.SpringDataAccountRepository;
import com.santander.challenge.transactions.infrastructure.persistence.jpa.SpringDataTransactionRepository;
import com.santander.challenge.transactions.infrastructure.persistence.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final SpringDataTransactionRepository springDataRepository;
    private final SpringDataAccountRepository accountRepository;


    @Override
    public void save(Transaction transaction) {
        AccountEntity accountEntity = accountRepository.findById(transaction.getAccountId()).orElseThrow();
        TransactionEntity entity = TransactionMapper.toEntity(transaction, accountEntity);
        springDataRepository.save(entity);
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId) {
        return springDataRepository.findByAccount_IdOrderByOccurredAtDesc(accountId)
                .stream()
                .map(TransactionMapper::toDomain)
                .toList();
    }
}
