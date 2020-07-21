package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.domain.utils.DataUtils;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewTransactionPresentation;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class NewTransactionCommandTest {

    @Test
    public void shouldReturnNewTransactionCommand() {

        // GIVEN
        NewTransactionPresentation newTransactionPresentation = createTransactionPresentation();

        // WHEN
        NewTransactionCommand newTransactionCommand = NewTransactionCommand.of(newTransactionPresentation);

        // THEN
        Assertions.assertThat(newTransactionCommand.getReference()).isEqualTo(newTransactionPresentation.getReference());
        Assertions.assertThat(newTransactionCommand.getAccountIban()).isEqualTo(newTransactionPresentation.getAccountIban());
        Assertions.assertThat(newTransactionCommand.getDate()).isEqualTo(newTransactionPresentation.getDate());
        Assertions.assertThat(newTransactionCommand.getAmount()).isEqualTo(newTransactionPresentation.getAmount());
        Assertions.assertThat(newTransactionCommand.getFee()).isEqualTo(newTransactionPresentation.getFee());
        Assertions.assertThat(newTransactionCommand.getDescription()).isEqualTo(newTransactionPresentation.getDescription());
    }

    private NewTransactionPresentation createTransactionPresentation() {

        return NewTransactionPresentation.builder()
                                         .reference(UUID.randomUUID().toString())
                                         .accountIban("ES5220951741879861123899")
                                         .date(DataUtils.FORMATTER.format(new Date()))
                                         .amount(52.79)
                                         .fee(3.50)
                                         .description("Payment in market")
                                         .build();
    }
}
