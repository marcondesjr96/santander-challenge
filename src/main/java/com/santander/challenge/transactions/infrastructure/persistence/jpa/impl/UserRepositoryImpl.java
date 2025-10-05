package com.santander.challenge.transactions.infrastructure.persistence.jpa.impl;

import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.domain.repository.UserRepository;
import com.santander.challenge.transactions.infrastructure.persistence.entity.UserEntity;
import com.santander.challenge.transactions.infrastructure.persistence.jpa.SpringDataUserRepository;
import com.santander.challenge.transactions.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springDataRepository;

    @Override
    public Optional<User> findById(UUID id) {
        return springDataRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return springDataRepository.findByLogin(login).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        return springDataRepository.findByCpf(cpf).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = springDataRepository.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(userEntity);
    }
}
