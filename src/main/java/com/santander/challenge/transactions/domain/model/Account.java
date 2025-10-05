package com.santander.challenge.transactions.domain.model;

import com.santander.challenge.transactions.domain.exception.ValidatePositiveAmountException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


public class Account {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;

    public Account(UUID id, UUID userId, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    public Account(UUID userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public void pay(BigDecimal amount) {
        validatePositive(amount);
        balance = balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public void deposit(BigDecimal amount, BigDecimal interestRateEx) {
        validatePositive(amount);
        BigDecimal interestRate = interestRateEx == null ? BigDecimal.ZERO : interestRateEx;

        if (balance.signum() < 0) {
            BigDecimal negative = balance.abs();
            BigDecimal fee = negative.multiply(interestRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal totalDue = negative.add(fee);


            if (amount.compareTo(totalDue) >= 0) {
                amount = amount.subtract(totalDue);
                balance = BigDecimal.ZERO.add(amount);
            } else {

                balance = totalDue.subtract(amount).negate();
            }
        } else {
            balance = balance.add(amount);
        }
        balance = balance.setScale(2, RoundingMode.HALF_UP);
    }

    private void validatePositive(BigDecimal value) {
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
