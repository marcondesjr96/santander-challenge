package com.santander.challenge.transactions.domain.repository;

import com.santander.challenge.transactions.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    Optional<User> findByLogin(String login);
    Optional<User> findByCpf(String cpf);
    User save(User user);
}
