package com.rjglez.backend.bank.transactions.domain.model;

import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.domain.utils.DataUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class TransactionEntityTest {

    private static Stream<Arguments> parametersProvided() {
        return Stream.of(
                Arguments.of(DataUtils.FORMATTER.format(new Date()), UUID.randomUUID().toString()),
                Arguments.of(null, UUID.randomUUID().toString()),
                Arguments.of(DataUtils.FORMATTER.format(new Date()), null),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("parametersProvided")
    public void shouldReturnTransactionEntityWhenDateAndOrReferenceAreNull(String date, String reference) {

        // GIVEN
        NewTransactionCommand newTransactionCommand = createTransactionCommandWithParameters(date, reference);

        // WHEN
        TransactionEntity transactionEntity = TransactionEntity.of(newTransactionCommand);
        transactionEntity.checkParameters();

        // THEN
        Assertions.assertThat(transactionEntity.getId()).isNotNull();
        Assertions.assertThat(transactionEntity.getAccount().getId()).isEqualTo(newTransactionCommand.getAccountIban());
        Assertions.assertThat(transactionEntity.getDate()).isNotNull();
        Assertions.assertThat(transactionEntity.getAmount()).isEqualTo(newTransactionCommand.getAmount());
        Assertions.assertThat(transactionEntity.getFee()).isEqualTo(newTransactionCommand.getFee());
        Assertions.assertThat(transactionEntity.getDescription()).isEqualTo(newTransactionCommand.getDescription());
    }

    @Test
    public void shouldReturnTransactionEntity() {

        // GIVEN
        NewTransactionCommand newTransactionCommand = DataUtils.createTransactionCommand();

        // WHEN
        TransactionEntity transactionEntity = TransactionEntity.of(newTransactionCommand);

        // THEN
        Assertions.assertThat(transactionEntity.getId()).isEqualTo(newTransactionCommand.getReference());
        Assertions.assertThat(transactionEntity.getAccount().getId()).isEqualTo(newTransactionCommand.getAccountIban());
        Assertions.assertThat(transactionEntity.getDate()).isEqualTo(newTransactionCommand.getDate());
        Assertions.assertThat(transactionEntity.getAmount()).isEqualTo(newTransactionCommand.getAmount());
        Assertions.assertThat(transactionEntity.getFee()).isEqualTo(newTransactionCommand.getFee());
        Assertions.assertThat(transactionEntity.getDescription()).isEqualTo(newTransactionCommand.getDescription());
    }

    @Test
    public void shouldGetAmountToProcessInTransactionToAddBalance() {

        // GIVEN
        NewTransactionCommand newTransactionCommand = DataUtils.createTransactionCommand();

        // WHEN
        TransactionEntity transactionEntity = TransactionEntity.of(newTransactionCommand);
        double            amountToProcess   = transactionEntity.getAmountToProcess();

        // THEN
        Assertions.assertThat(amountToProcess).isEqualTo(49.29);
    }

    @Test
    public void shouldGetAmountToProcessInTransactionToDeductBalance() {

        // GIVEN
        NewTransactionCommand newTransactionCommand = DataUtils.createTransactionCommand();

        // WHEN
        TransactionEntity transactionEntity = TransactionEntity.of(newTransactionCommand);
        transactionEntity.setAmount(-100.30);
        double amountToProcess = transactionEntity.getAmountToProcess();

        // THEN
        Assertions.assertThat(amountToProcess).isEqualTo(-103.80);
    }

    private NewTransactionCommand createTransactionCommandWithParameters(String date, String reference) {

        return NewTransactionCommand.builder()
                                    .reference(reference)
                                    .accountIban("ES5220951741879861123899")
                                    .date(date)
                                    .amount(52.79)
                                    .fee(3.50)
                                    .description("Payment in market")
                                    .build();
    }
}
