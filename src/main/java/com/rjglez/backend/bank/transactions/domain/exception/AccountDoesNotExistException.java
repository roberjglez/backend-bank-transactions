package com.rjglez.backend.bank.transactions.domain.exception;

public class AccountDoesNotExistException extends RuntimeException {
    public AccountDoesNotExistException(String accountIban) {
        super("Account with IBAN " + accountIban + " does not exist");
    }
}
