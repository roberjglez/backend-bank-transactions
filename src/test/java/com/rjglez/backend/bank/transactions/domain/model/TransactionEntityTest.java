package com.rjglez.backend.bank.transactions.domain.model;

import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.domain.utils.DataUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionEntityTest {

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

}
