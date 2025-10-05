package com.santander.challenge.transactions.domain.repository;

import com.santander.challenge.transactions.domain.model.Account;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Optional<Account> findById(UUID id);
    void save(Account account);
}
