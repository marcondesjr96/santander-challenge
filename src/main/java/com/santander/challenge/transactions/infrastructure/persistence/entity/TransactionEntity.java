package com.santander.challenge.transactions.infrastructure.persistence.entity;

import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "transaction",
       indexes = {@Index(name = "idx_tx_account_date", columnList = "account_id, occurred_at")})
public class TransactionEntity extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tx_account"))
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TransactionTypeEnum type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

}
