package com.santander.challenge.transactions.infrastructure.persistence.jpa;

import com.santander.challenge.transactions.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByUserId (UUID userId);
}
