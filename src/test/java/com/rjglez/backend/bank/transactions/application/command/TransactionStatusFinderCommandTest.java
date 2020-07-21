package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.domain.exception.ChannelNotProvidedException;
import com.rjglez.backend.bank.transactions.domain.exception.IncorrectChannelProvidedException;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.SearchTransactionStatusPresentation;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionStatusFinderCommandTest {

    @Test
    public void shouldReturnTransactionStatusFinderCommand() {

        // GIVEN
        SearchTransactionStatusPresentation searchTransactionStatusPresentation = SearchTransactionStatusPresentation.builder()
                                                                                                                     .reference("85b37cac-e8c2-46f4-8c77-e11f0cff16b1")
                                                                                                                     .channel("CLIENT")
                                                                                                                     .build();

        // WHEN
        TransactionStatusFinderCommand transactionStatusFinderCommand = TransactionStatusFinderCommand.of(searchTransactionStatusPresentation);

        // THEN
        Assertions.assertThat(transactionStatusFinderCommand.getReference()).isEqualTo(searchTransactionStatusPresentation.getReference());
        Assertions.assertThat(transactionStatusFinderCommand.getChannel()).isEqualTo(searchTransactionStatusPresentation.getChannel());
    }

    @Test
    public void shouldThrowChannelNotProvidedException() {

        org.junit.jupiter.api.Assertions.assertThrows(ChannelNotProvidedException.class, () -> {

            // GIVEN
            SearchTransactionStatusPresentation searchTransactionStatusPresentation = SearchTransactionStatusPresentation.builder()
                                                                                                                         .reference("85b37cac-e8c2-46f4-8c77-e11f0cff16b1")
                                                                                                                         .build();

            // WHEN
            TransactionStatusFinderCommand.of(searchTransactionStatusPresentation);
        });
    }

    @Test
    public void shouldThrowIncorrectChannelProvidedException() {

        org.junit.jupiter.api.Assertions.assertThrows(IncorrectChannelProvidedException.class, () -> {

            // GIVEN
            SearchTransactionStatusPresentation searchTransactionStatusPresentation = SearchTransactionStatusPresentation.builder()
                                                                                                                         .reference("85b37cac-e8c2-46f4-8c77-e11f0cff16b1")
                                                                                                                         .channel("CHANNEL")
                                                                                                                         .build();

            // WHEN
            TransactionStatusFinderCommand.of(searchTransactionStatusPresentation);
        });
    }
}
