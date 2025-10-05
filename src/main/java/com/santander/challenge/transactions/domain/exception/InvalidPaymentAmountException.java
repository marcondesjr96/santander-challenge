package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class InvalidPaymentAmountException extends BaseCustomException {
    public InvalidPaymentAmountException() {
        super(ErrorCodeEnum.T0003, HttpStatus.BAD_REQUEST);
    }
}
