package com.santander.challenge.transactions.infrastructure.persistence.mapper;

import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.infrastructure.persistence.entity.AccountEntity;
import com.santander.challenge.transactions.infrastructure.persistence.entity.UserEntity;

import java.util.UUID;

public class AccountMapper {

    public static Account toDomain(AccountEntity entity) {
        if (entity == null) return null;
        return new Account(
                entity.getId(),
                entity.getUser().getId(),
                entity.getBalance()
        );
    }

    public static AccountEntity toEntity(Account domain, UserEntity userEntity) {
        if (domain == null) return null;
        AccountEntity entity = new AccountEntity();
        entity.setId(domain.getId() != null ? domain.getId() : UUID.randomUUID());
        entity.setUser(userEntity);
        entity.setBalance(domain.getBalance());
        return entity;
    }
}
