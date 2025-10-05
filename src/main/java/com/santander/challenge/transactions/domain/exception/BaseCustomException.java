package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseCustomException extends RuntimeException {

    private final ErrorCodeEnum errorCode;
    private final HttpStatus httpStatus;

    public BaseCustomException(ErrorCodeEnum errorCode, HttpStatus httpStatus) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public BaseCustomException(ErrorCodeEnum errorCode, HttpStatus httpStatus, String... args) {
        super(formatMessage(errorCode, args));
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    private static String formatMessage(ErrorCodeEnum errorCode, String... args) {
        return String.format(errorCode.getMessage(), (Object[]) args);
    }
}

