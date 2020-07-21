package com.rjglez.backend.bank.transactions.domain.exception;

public class ChannelNotProvidedException extends RuntimeException {
    public ChannelNotProvidedException() {
        super("Channel not provided");
    }
}
