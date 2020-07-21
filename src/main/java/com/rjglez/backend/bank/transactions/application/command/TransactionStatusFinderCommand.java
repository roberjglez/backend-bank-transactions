package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.domain.exception.ChannelNotProvidedException;
import com.rjglez.backend.bank.transactions.domain.exception.IncorrectChannelProvidedException;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.SearchTransactionStatusPresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatusFinderCommand {

    private String reference;
    private String channel;

    public static TransactionStatusFinderCommand of(SearchTransactionStatusPresentation searchTransactionStatusPresentation) {

        String reference = searchTransactionStatusPresentation.getReference();
        String channel   = searchTransactionStatusPresentation.getChannel();

        checkChannelProvided(channel);

        return TransactionStatusFinderCommand.builder()
                                             .reference(reference)
                                             .channel(channel)
                                             .build();

    }

    private static void checkChannelProvided(String channel) {

        if (Objects.isNull(channel)) {
            log.error("Channel not provided, will throw ChannelNotProvidedException");
            throw new ChannelNotProvidedException();
        } else {
            boolean correctChannel = isCorrectChannel(channel);
            if (!correctChannel) {
                log.error("Channel provided not correct: {}", channel);
                throw new IncorrectChannelProvidedException(channel);
            }
        }
    }

    private static boolean isCorrectChannel(String channel) {
        return channel.equals("CLIENT") || channel.equals("ATM") || channel.equals("INTERNAL");
    }
}
