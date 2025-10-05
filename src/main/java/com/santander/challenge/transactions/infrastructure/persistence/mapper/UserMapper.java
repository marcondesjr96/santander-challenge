package com.santander.challenge.transactions.infrastructure.persistence.mapper;

import com.santander.challenge.transactions.domain.model.Cpf;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.infrastructure.persistence.entity.UserEntity;

import java.util.UUID;

public class UserMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getId(),
                entity.getFullName(),
                new Cpf(entity.getCpf()),
                entity.getLogin(),
                entity.getPasswordHash()
        );
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId() != null ? domain.getId() : UUID.randomUUID());
        entity.setFullName(domain.getFullName());
        entity.setCpf(domain.getCpf().getValue());
        entity.setLogin(domain.getLogin());
        entity.setPasswordHash(domain.getPasswordHash());
        return entity;
    }
}
