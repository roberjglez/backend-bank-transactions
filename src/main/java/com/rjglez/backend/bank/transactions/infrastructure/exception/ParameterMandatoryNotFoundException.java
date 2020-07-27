package com.rjglez.backend.bank.transactions.infrastructure.exception;

public class ParameterMandatoryNotFoundException extends RuntimeException {
    public ParameterMandatoryNotFoundException(String parameter) {
        super("Parameter mandatory " + parameter + " not found in the request body");
    }
}
