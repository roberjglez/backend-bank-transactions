package com.rjglez.backend.bank.transactions.domain.exception;

public class IncorrectChannelProvidedException extends RuntimeException {
    public IncorrectChannelProvidedException(String channel) {

        super("Incorrect channel provided: " + channel);
    }
}
