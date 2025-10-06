package com.santander.challenge.transactions.infrastructure.persistence.jpa.impl;

import com.santander.challenge.transactions.domain.exception.AccountNotFoundException;
import com.santander.challenge.transactions.domain.exception.UserNotFoundException;
import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.infrastructure.persistence.entity.AccountEntity;
import com.santander.challenge.transactions.infrastructure.persistence.entity.UserEntity;
import com.santander.challenge.transactions.infrastructure.persistence.jpa.SpringDataAccountRepository;
import com.santander.challenge.transactions.infrastructure.persistence.jpa.SpringDataUserRepository;
import com.santander.challenge.transactions.infrastructure.persistence.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final SpringDataAccountRepository springDataRepository;
    private final SpringDataUserRepository userRepository;

    @Override
    public Optional<Account> findById(UUID id) {
        return springDataRepository.findById(id).map(AccountMapper::toDomain);
    }

    @Override
    public Optional<Account> findByUserId(UUID userId) {
        return springDataRepository.findByUserId(userId).map(AccountMapper::toDomain);
    }

    @Override
    public Account save(Account account) {
        UserEntity userEntity = userRepository.findById(account.getUserId()).orElseThrow(UserNotFoundException::new);
        AccountEntity accountEntity = AccountMapper.toEntity(account, userEntity);
        return AccountMapper.toDomain(springDataRepository.save(accountEntity));
    }

    @Override
    public void update(Account account) {
        UserEntity userEntity = userRepository.findById(account.getUserId()).orElseThrow(UserNotFoundException::new);
        AccountEntity accountEntity = AccountMapper.toEntityUpdate(account, userEntity);
        springDataRepository.save(accountEntity);
    }
}
