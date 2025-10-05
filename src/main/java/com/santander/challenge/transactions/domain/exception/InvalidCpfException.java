package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class InvalidCpfException extends BaseCustomException {
    public InvalidCpfException() {
        super(ErrorCodeEnum.U0002, HttpStatus.BAD_REQUEST);
    }
}
