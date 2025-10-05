package com.santander.challenge.transactions.infrastructure.persistence.jpa;

import com.santander.challenge.transactions.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findByAccount_IdOrderByOccurredAtDesc(UUID accountId);
}