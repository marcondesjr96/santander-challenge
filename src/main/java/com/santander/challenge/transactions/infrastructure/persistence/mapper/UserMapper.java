package com.santander.challenge.transactions.infrastructure.persistence.mapper;

import com.santander.challenge.transactions.domain.model.Cpf;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.infrastructure.persistence.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getId(),
                entity.getFullName(),
                new Cpf(entity.getCpf()),
                entity.getLogin(),
                entity.getPasswordHash()
        );
    }

    public static UserEntity toEntity(User user) {
        if (user == null) return null;
        return UserEntity
                .builder()
                .fullName(user.getFullName())
                .cpf(user.getCpf().getValue())
                .login(user.getLogin())
                .passwordHash(user.getPasswordHash())
                .build();
    }
}
