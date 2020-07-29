package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.domain.exception.IncorrectChannelProvidedException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionStatusFinderCommandTest {

    @Test
    public void shouldReturnTransactionStatusFinderCommand() {

        // GIVEN
        String reference = "85b37cac-e8c2-46f4-8c77-e11f0cff16b1";
        String channel   = "CLIENT";

        // WHEN
        TransactionStatusFinderCommand transactionStatusFinderCommand = TransactionStatusFinderCommand.of(reference, channel);

        // THEN
        Assertions.assertThat(transactionStatusFinderCommand.getReference()).isEqualTo(reference);
        Assertions.assertThat(transactionStatusFinderCommand.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldReturnTransactionStatusFinderCommandWhenChannelIsNotProvided() {

        // GIVEN
        String reference = "85b37cac-e8c2-46f4-8c77-e11f0cff16b1";

        // WHEN
        TransactionStatusFinderCommand transactionStatusFinderCommand = TransactionStatusFinderCommand.of(reference, null);

        // THEN
        Assertions.assertThat(transactionStatusFinderCommand.getReference()).isEqualTo(reference);
    }

    @Test
    public void shouldThrowIncorrectChannelProvidedException() {

        org.junit.jupiter.api.Assertions.assertThrows(IncorrectChannelProvidedException.class, () -> {

            // GIVEN
            String reference = "85b37cac-e8c2-46f4-8c77-e11f0cff16b1";
            String channel   = "CHANNEL";

            // WHEN
            TransactionStatusFinderCommand.of(reference, channel);
        });
    }
}
