package com.rjglez.backend.bank.transactions.domain.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String accountIban) {
        super("Account with IBAN " + accountIban + " already exists in the system");
    }
}
