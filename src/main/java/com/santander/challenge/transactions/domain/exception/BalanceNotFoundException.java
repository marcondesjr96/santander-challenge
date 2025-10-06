package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class BalanceNotFoundException extends BaseCustomException {
    public BalanceNotFoundException() {
        super(ErrorCodeEnum.U0005, HttpStatus.NOT_FOUND);
    }
}
