package com.santander.challenge.transactions.infrastructure.persistence.jpa.impl;

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
    public void save(Account account) {
        UserEntity userEntity = userRepository.findById(account.getUserId()).orElseThrow();
        AccountEntity accountEntity = AccountMapper.toEntity(account, userEntity);
        springDataRepository.save(accountEntity);
    }
}
