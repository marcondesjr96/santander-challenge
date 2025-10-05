package com.santander.challenge.transactions.domain.model;

import com.santander.challenge.transactions.domain.exception.ValidatePositiveAmountException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


public class Account {
    private final UUID id;
    private final UUID userId;
    private BigDecimal balance;

    public Account(UUID id, UUID userId, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    /**
     * Paga uma conta (pode negativar)
     */
    public void pay(BigDecimal amount) {
        validatePositive(amount, "Payment amount must be positive");
        balance = balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Depósito: se estiver negativo, abate o principal negativo + juros.
     * interestRateEx: 0.02 para 2% (parâmetro para manter domínio puro e testável).
     */
    public void deposit(BigDecimal amount, BigDecimal interestRateEx) {
        validatePositive(amount, "Deposit amount must be positive");
        BigDecimal interestRate = interestRateEx == null ? BigDecimal.ZERO : interestRateEx;

        if (balance.signum() < 0) {
            BigDecimal negative = balance.abs(); // principal negativo
            BigDecimal fee = negative.multiply(interestRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal totalDue = negative.add(fee);

            // primeiro quita a dívida negativa + juros
            if (amount.compareTo(totalDue) >= 0) {
                amount = amount.subtract(totalDue);
                balance = BigDecimal.ZERO.add(amount);
            } else {
                // ainda negativo
                balance = totalDue.subtract(amount).negate();
            }
        } else {
            balance = balance.add(amount);
        }
        balance = balance.setScale(2, RoundingMode.HALF_UP);
    }

    private void validatePositive(BigDecimal value, String msg) {
        if (value == null || value.setScale(2, RoundingMode.HALF_UP).signum() <= 0)
            throw new ValidatePositiveAmountException();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
