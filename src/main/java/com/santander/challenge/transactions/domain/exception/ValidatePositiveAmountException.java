package com.santander.challenge.transactions.domain.exception;

import com.santander.challenge.transactions.domain.model.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class ValidatePositiveAmountException extends BaseCustomException {

    public ValidatePositiveAmountException() { super(ErrorCodeEnum.T0001, HttpStatus.BAD_REQUEST); }
}
