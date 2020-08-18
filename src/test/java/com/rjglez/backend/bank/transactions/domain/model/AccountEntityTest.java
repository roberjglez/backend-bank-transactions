package com.rjglez.backend.bank.transactions.domain.model;

import com.rjglez.backend.bank.transactions.application.command.NewAccountCommand;
import com.rjglez.backend.bank.transactions.domain.utils.DataUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AccountEntityTest {

    private AccountEntity accountEntity;

    @Before
    public void setUp() {

        this.accountEntity = DataUtils.mockAccountEntity();
    }

    @Test
    public void shouldReturnAccountEntity() {

        // GIVEN
        NewAccountCommand newAccountCommand = NewAccountCommand.builder()
                                                               .iban("ES3930294948393")
                                                               .balance(3000.45)
                                                               .build();

        // WHEN
        AccountEntity accountEntity = AccountEntity.of(newAccountCommand);

        // THEN
        Assertions.assertThat(accountEntity.getId()).isEqualTo(newAccountCommand.getIban());
        Assertions.assertThat(accountEntity.getBalance()).isEqualTo(newAccountCommand.getBalance());
    }

    @Test
    public void shouldReturnTransactionAllowed() {

        // GIVEN
        double amountToProcess = 30.20;

        // WHEN
        boolean isTransactionAllowed = accountEntity.isTransactionAllowed(amountToProcess);

        // THEN
        Assertions.assertThat(isTransactionAllowed).isTrue();
    }

    @Test
    public void shouldReturnTransactionNotAllowed() {

        // GIVEN
        double amountToProcess = -430.6;

        // WHEN
        boolean isTransactionAllowed = accountEntity.isTransactionAllowed(amountToProcess);

        // THEN
        Assertions.assertThat(isTransactionAllowed).isFalse();
    }

    @Test
    public void shouldModifyBalance() {

        // GIVEN
        double oldBalance      = accountEntity.getBalance();
        double amountToProcess = 430.6;

        // WHEN
        accountEntity.modifyBalance(amountToProcess);

        // THEN
        Assertions.assertThat(accountEntity.getBalance()).isEqualTo(oldBalance + amountToProcess);
    }

    @Test
    public void shouldAddTransaction() {

        // GIVEN
        List<TransactionEntity> transactionsList     = accountEntity.getTransactions();
        int                     sizeTransactionsList = transactionsList.size();

        // WHEN
        accountEntity.addTransaction(transactionsList.get(0));

        // THEN
        Assertions.assertThat(accountEntity.getTransactions().size()).isEqualTo(sizeTransactionsList + 1);
    }

}
