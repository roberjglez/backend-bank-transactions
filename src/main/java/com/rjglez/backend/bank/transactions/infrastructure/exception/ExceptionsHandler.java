package com.rjglez.backend.bank.transactions.infrastructure.exception;

import com.rjglez.backend.bank.transactions.domain.exception.AccountDoesNotExistException;
import com.rjglez.backend.bank.transactions.domain.exception.ChannelNotProvidedException;
import com.rjglez.backend.bank.transactions.domain.exception.IncorrectChannelProvidedException;
import com.rjglez.backend.bank.transactions.domain.exception.InsufficientBalanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler(value = AccountDoesNotExistException.class)
    public ResponseEntity<Object> handlerAccountDoesNotExistException(AccountDoesNotExistException e) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(value = InsufficientBalanceException.class)
    public ResponseEntity<Object> handlerInsufficientBalanceException(InsufficientBalanceException e) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value = ChannelNotProvidedException.class)
    public ResponseEntity<Object> handlerChannelNotProvidedException(ChannelNotProvidedException e) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value = IncorrectChannelProvidedException.class)
    public ResponseEntity<Object> handlerIncorrectChannelProvidedException(IncorrectChannelProvidedException e) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
