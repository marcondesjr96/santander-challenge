package com.santander.challenge.transactions.infrastructure.persistence.jpa.impl;

import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.domain.repository.UserRepository;
import com.santander.challenge.transactions.infrastructure.persistence.mapper.DomainEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springDataRepository;
    private final DomainEntityMapper mapper;

    public UserRepositoryImpl(SpringDataUserRepository springDataRepository, DomainEntityMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return springDataRepository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        return springDataRepository.findByCpf(cpf).map(mapper::toDomain);
    }

    @Override
    public void save(User user) {
        springDataRepository.save(mapper.toEntity(user));
    }
}
