package com.rjglez.backend.bank.transactions.domain.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String accountIban) {
        super("Insufficient balance to process the transaction in the account with IBAN " + accountIban);
    }
}
