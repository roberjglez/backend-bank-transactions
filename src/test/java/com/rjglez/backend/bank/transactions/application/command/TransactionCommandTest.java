package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.infrastructure.presentation.TransactionPresentation;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class TransactionCommandTest {

    @Test
    public void shouldReturnTransactionCommand() {

        // GIVEN
        TransactionPresentation transactionPresentation = createTransactionPresentation();

        // WHEN
        TransactionCommand transactionCommand = TransactionCommand.of(transactionPresentation);

        // THEN
        Assertions.assertThat(transactionCommand.getReference()).isEqualTo(transactionPresentation.getReference());
        Assertions.assertThat(transactionCommand.getAccountIban()).isEqualTo(transactionPresentation.getAccountIban());
        Assertions.assertThat(transactionCommand.getDate()).isEqualTo(transactionPresentation.getDate());
        Assertions.assertThat(transactionCommand.getAmount()).isEqualTo(transactionPresentation.getAmount());
        Assertions.assertThat(transactionCommand.getFee()).isEqualTo(transactionPresentation.getFee());
        Assertions.assertThat(transactionCommand.getDescription()).isEqualTo(transactionPresentation.getDescription());
    }

    private TransactionPresentation createTransactionPresentation() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return TransactionPresentation.builder()
                                      .reference(UUID.randomUUID().toString())
                                      .accountIban("ES5220951741879861123899")
                                      .date(formatter.format(new Date()))
                                      .amount(52.79)
                                      .fee(3.50)
                                      .description("Payment in market")
                                      .build();
    }
}
