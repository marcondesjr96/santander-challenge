package com.santander.challenge.transactions.domain.model.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    T0001("T0001", "Amount must be positive."),
    T0003("T0003", "Invalid payment amount."),

    A0001("A0001", "Account not found."),

    U0001("U0001", "User not found."),
    U0002("U0002", "Invalid CPF format."),
    U0003("U0003", "Login already exists"),
    U0004("U0004", "CPF already registered"),;

    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
