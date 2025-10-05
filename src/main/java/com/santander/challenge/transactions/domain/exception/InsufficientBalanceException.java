package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends BaseCustomException {
    public InsufficientBalanceException() {
        super(ErrorCodeEnum.T0002, HttpStatus.BAD_REQUEST);
    }
}
