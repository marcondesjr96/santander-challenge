package com.santander.challenge.transactions.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum TransactionTypeEnum {
    DEPOSIT(1L, "Deposit"),
    PAYMENT(2L, "Payment");

    private final Long id;
    private final String type;

    public static TransactionTypeEnum getById(Long id) {
        return Arrays.stream(TransactionTypeEnum.values())
                .filter(value -> value.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static TransactionTypeEnum getByType(String type) {
        return Arrays.stream(TransactionTypeEnum.values())
                .filter(value -> value.getType().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}
