package com.santander.challenge.transactions.infrastructure.persistence.mapper;

import com.santander.challenge.transactions.domain.model.Cpf;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.infrastructure.persistence.entity.UserEntity;

import java.util.UUID;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getFullName(),
                new Cpf(entity.getCpf()),
                entity.getLogin(),
                entity.getPasswordHash()
        );
    }

    //TODO usar o builder
    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;
        UserEntity entity = new UserEntity();
        entity.setFullName(domain.getFullName());
        entity.setCpf(domain.getCpf().getValue());
        entity.setLogin(domain.getLogin());
        entity.setPasswordHash(domain.getPasswordHash());
        return entity;
    }
}
