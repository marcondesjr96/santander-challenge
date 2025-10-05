package com.santander.challenge.transactions.infrastructure.persistence.jpa.impl;

import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.repository.TransactionRepository;
import com.santander.challenge.transactions.infrastructure.persistence.mapper.DomainEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final SpringDataTransactionRepository springDataRepository;
    private final SpringDataAccountRepository accountRepository;
    private final DomainEntityMapper mapper;

    public TransactionRepositoryImpl(SpringDataTransactionRepository springDataRepository,
                                     SpringDataAccountRepository accountRepository,
                                     DomainEntityMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Transaction transaction) {
        var accountEntity = accountRepository.findById(transaction.getAccountId()).orElseThrow();
        var entity = mapper.toEntity(transaction, accountEntity);
        springDataRepository.save(entity);
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId) {
        return springDataRepository.findByAccount_IdOrderByOccurredAtDesc(accountId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
