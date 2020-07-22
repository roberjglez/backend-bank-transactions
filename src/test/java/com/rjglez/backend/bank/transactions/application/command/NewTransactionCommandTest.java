package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.domain.utils.DataUtils;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewTransactionPresentation;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class NewTransactionCommandTest {

    @ParameterizedTest
    @MethodSource("parametersProvided")
    public void shouldReturnNewTransactionCommandWhenDateAndOrReferenceAreNull(String date, String reference) {

        // GIVEN
        NewTransactionPresentation newTransactionPresentation = createTransactionPresentation(date, reference);

        // WHEN
        NewTransactionCommand newTransactionCommand = NewTransactionCommand.of(newTransactionPresentation);

        // THEN
        Assertions.assertThat(newTransactionCommand.getReference()).isNotNull();
        Assertions.assertThat(newTransactionCommand.getAccountIban()).isEqualTo(newTransactionPresentation.getAccountIban());
        Assertions.assertThat(newTransactionCommand.getDate()).isNotNull();
        Assertions.assertThat(newTransactionCommand.getAmount()).isEqualTo(newTransactionPresentation.getAmount());
        Assertions.assertThat(newTransactionCommand.getFee()).isEqualTo(newTransactionPresentation.getFee());
        Assertions.assertThat(newTransactionCommand.getDescription()).isEqualTo(newTransactionPresentation.getDescription());
    }

    @Test
    public void shouldReturnNewTransactionCommand() {

        // GIVEN
        NewTransactionPresentation newTransactionPresentation = createTransactionPresentation(UUID.randomUUID().toString(), DataUtils.FORMATTER.format(new Date()));

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

    private static Stream<Arguments> parametersProvided() {
        return Stream.of(
                Arguments.of(DataUtils.FORMATTER.format(new Date()), UUID.randomUUID().toString()),
                Arguments.of(null, UUID.randomUUID().toString()),
                Arguments.of(DataUtils.FORMATTER.format(new Date()), null),
                Arguments.of(null, null)
        );
    }

    private NewTransactionPresentation createTransactionPresentation(String date, String reference) {

        return NewTransactionPresentation.builder()
                                         .reference(reference)
                                         .accountIban("ES5220951741879861123899")
                                         .date(date)
                                         .amount(52.79)
                                         .fee(3.50)
                                         .description("Payment in market")
                                         .build();
    }
}
