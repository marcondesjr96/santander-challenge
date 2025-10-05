package com.santander.challenge.transactions.domain.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<Object> handleCustom(BaseCustomException ex) {
        var body = Map.of(
            "code", ex.getErrorCode().getCode(),
            "message", ex.getErrorCode().getMessage(),
            "status", ex.getHttpStatus().value()
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }
}
