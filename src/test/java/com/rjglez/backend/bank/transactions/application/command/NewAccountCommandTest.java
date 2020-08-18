package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewAccountPresentation;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NewAccountCommandTest {

    @Test
    public void shouldReturnNewAccountCommand() {

        // GIVEN
        NewAccountPresentation newAccountPresentation = NewAccountPresentation.builder()
                                                                              .iban("ES5220951741879861123899")
                                                                              .balance(3000.45)
                                                                              .build();

        // WHEN
        NewAccountCommand newAccountCommand = NewAccountCommand.of(newAccountPresentation);

        // THEN
        Assertions.assertThat(newAccountCommand.getIban()).isEqualTo(newAccountPresentation.getIban());
        Assertions.assertThat(newAccountCommand.getBalance()).isEqualTo(newAccountPresentation.getBalance());
    }
}
