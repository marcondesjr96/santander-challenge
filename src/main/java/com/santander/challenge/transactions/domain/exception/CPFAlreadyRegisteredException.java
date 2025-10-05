package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class CPFAlreadyRegisteredException extends BaseCustomException {
    public CPFAlreadyRegisteredException() {
        super(ErrorCodeEnum.U0004, HttpStatus.BAD_REQUEST);
    }
}
