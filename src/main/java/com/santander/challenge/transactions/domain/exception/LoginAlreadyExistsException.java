package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class LoginAlreadyExistsException extends BaseCustomException {
    public LoginAlreadyExistsException() {
        super(ErrorCodeEnum.U0003, HttpStatus.BAD_REQUEST);
    }
}
