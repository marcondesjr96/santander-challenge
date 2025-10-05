package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseCustomException {
    public UserNotFoundException() {
        super(ErrorCodeEnum.U0001, HttpStatus.NOT_FOUND);
    }
}
