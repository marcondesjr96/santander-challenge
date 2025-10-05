package com.santander.challenge.transactions.infrastructure.persistence.jpa.impl;

import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.infrastructure.persistence.entity.AccountEntity;
import com.santander.challenge.transactions.infrastructure.persistence.jpa.SpringDataAccountRepository;
import com.santander.challenge.transactions.infrastructure.persistence.mapper.DomainEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final SpringDataAccountRepository springDataRepository;
    private final SpringDataUserRepository userRepository;
    private final DomainEntityMapper mapper;

    public AccountRepositoryImpl(SpringDataAccountRepository springDataRepository,
                                 SpringDataUserRepository userRepository,
                                 DomainEntityMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void save(Account account) {
        var userEntity = userRepository.findById(account.getUserId()).orElseThrow();
        var entity = mapper.toEntity(account, userEntity);
        springDataRepository.save(entity);
    }
}
