package com.rjglez.backend.bank.transactions.infrastructure.exception;

import com.rjglez.backend.bank.transactions.domain.exception.AccountDoesNotExistException;
import com.rjglez.backend.bank.transactions.domain.exception.ChannelNotProvidedException;
import com.rjglez.backend.bank.transactions.domain.exception.IncorrectChannelProvidedException;
import com.rjglez.backend.bank.transactions.domain.exception.InsufficientBalanceException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionsHandlerTest {

    @Test
    public void shouldReturnHttpStatusNotFoundWhenThrownAccountDoesNotExistException() {
        ExceptionsHandler      handler  = new ExceptionsHandler();
        ResponseEntity<Object> response = handler.handlerAccountDoesNotExistException(new AccountDoesNotExistException("Account with IBAN XXX does not exist"));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturnHttpStatusBadRequestWhenThrownInsufficientBalanceException() {
        ExceptionsHandler      handler  = new ExceptionsHandler();
        ResponseEntity<Object> response = handler.handlerInsufficientBalanceException(new InsufficientBalanceException("Insufficient balance to process the transaction in the account with IBAN XXX"));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnHttpStatusBadRequestWhenThrownChannelNotProvidedException() {
        ExceptionsHandler      handler  = new ExceptionsHandler();
        ResponseEntity<Object> response = handler.handlerChannelNotProvidedException(new ChannelNotProvidedException());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnHttpStatusBadRequestWhenThrownIncorrectChannelProvidedException() {
        ExceptionsHandler      handler  = new ExceptionsHandler();
        ResponseEntity<Object> response = handler.handlerIncorrectChannelProvidedException(new IncorrectChannelProvidedException("Incorrect channel provided"));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}