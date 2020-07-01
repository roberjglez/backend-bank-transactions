package com.rjglez.backend.bank.transactions.domain.model;

import com.rjglez.backend.bank.transactions.application.command.TransactionCommand;
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

    @ParameterizedTest
    @MethodSource("parametersProvided")
    public void shouldReturnTransactionEntityWhenDateAndOrReferenceAreNull(String date, String reference) {

        // GIVEN
        TransactionCommand transactionCommand = createTransactionCommandWithParameters(date, reference);

        // WHEN
        TransactionEntity transactionEntity = TransactionEntity.of(transactionCommand);
        transactionEntity.checkParameters();

        // THEN
        Assertions.assertThat(transactionEntity.getId()).isNotNull();
        Assertions.assertThat(transactionEntity.getAccount().getId()).isEqualTo(transactionCommand.getAccountIban());
        Assertions.assertThat(transactionEntity.getDate()).isNotNull();
        Assertions.assertThat(transactionEntity.getAmount()).isEqualTo(transactionCommand.getAmount());
        Assertions.assertThat(transactionEntity.getFee()).isEqualTo(transactionCommand.getFee());
        Assertions.assertThat(transactionEntity.getDescription()).isEqualTo(transactionCommand.getDescription());
    }

    @Test
    public void shouldReturnTransactionEntity() {

        // GIVEN
        TransactionCommand transactionCommand = DataUtils.createTransactionCommand();

        // WHEN
        TransactionEntity transactionEntity = TransactionEntity.of(transactionCommand);

        // THEN
        Assertions.assertThat(transactionEntity.getId()).isEqualTo(transactionCommand.getReference());
        Assertions.assertThat(transactionEntity.getAccount().getId()).isEqualTo(transactionCommand.getAccountIban());
        Assertions.assertThat(transactionEntity.getDate()).isEqualTo(transactionCommand.getDate());
        Assertions.assertThat(transactionEntity.getAmount()).isEqualTo(transactionCommand.getAmount());
        Assertions.assertThat(transactionEntity.getFee()).isEqualTo(transactionCommand.getFee());
        Assertions.assertThat(transactionEntity.getDescription()).isEqualTo(transactionCommand.getDescription());
    }

    @Test
    public void shouldGetAmountToProcessInTransactionToAddBalance() {

        // GIVEN
        TransactionCommand transactionCommand = DataUtils.createTransactionCommand();

        // WHEN
        TransactionEntity transactionEntity = TransactionEntity.of(transactionCommand);
        double            amountToProcess   = transactionEntity.getAmountToProcess();

        // THEN
        Assertions.assertThat(amountToProcess).isEqualTo(49.29);
    }

    @Test
    public void shouldGetAmountToProcessInTransactionToDeductBalance() {

        // GIVEN
        TransactionCommand transactionCommand = DataUtils.createTransactionCommand();

        // WHEN
        TransactionEntity transactionEntity = TransactionEntity.of(transactionCommand);
        transactionEntity.setAmount(-100.30);
        double amountToProcess = transactionEntity.getAmountToProcess();

        // THEN
        Assertions.assertThat(amountToProcess).isEqualTo(-103.80);
    }

    private static Stream<Arguments> parametersProvided() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return Stream.of(
                Arguments.of(formatter.format(new Date()), UUID.randomUUID().toString()),
                Arguments.of(null, UUID.randomUUID().toString()),
                Arguments.of(formatter.format(new Date()), null),
                Arguments.of(null, null)
        );
    }

    private TransactionCommand createTransactionCommandWithParameters(String date, String reference) {

        return TransactionCommand.builder()
                                 .reference(reference)
                                 .accountIban("ES5220951741879861123899")
                                 .date(date)
                                 .amount(52.79)
                                 .fee(3.50)
                                 .description("Payment in market")
                                 .build();
    }
}
