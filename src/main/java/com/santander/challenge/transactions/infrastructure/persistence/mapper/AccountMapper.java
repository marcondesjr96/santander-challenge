package com.santander.challenge.transactions.infrastructure.persistence.mapper;

import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.model.enums.StatusEnum;
import com.santander.challenge.transactions.infrastructure.persistence.entity.AccountEntity;
import com.santander.challenge.transactions.infrastructure.persistence.entity.UserEntity;

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
        entity.setUser(userEntity);
        entity.setBalance(domain.getBalance());
        return entity;
    }

    //TODO user Builder
    public static AccountEntity toEntityUpdate(Account account, UserEntity userEntity) {
        if (account == null) return null;
        return AccountEntity
                .builder()
                .id(account.getId())
                .user(userEntity)
                .balance(account.getBalance())
                .status(StatusEnum.ACTIVE)
                .build();
    }
}
