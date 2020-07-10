package com.rjglez.backend.bank.transactions.application.command;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionFinderCommandTest {

    @Test
    public void shouldReturnTransactionFinderCommand() {

        // GIVEN
        String accountIban = "ES5220951741879861123899";
        String sorting     = "ascending";

        // WHEN
        TransactionFinderCommand transactionFinderCommand = TransactionFinderCommand.of(accountIban, sorting);

        // THEN
        Assertions.assertThat(transactionFinderCommand.getAccountIban()).isEqualTo(accountIban);
        Assertions.assertThat(transactionFinderCommand.getSorting()).isEqualTo(sorting);
    }
}
