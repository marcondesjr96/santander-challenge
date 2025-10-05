package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BaseCustomException {
    public AccountNotFoundException() {
        super(ErrorCodeEnum.A0001, HttpStatus.NOT_FOUND);
    }
}
