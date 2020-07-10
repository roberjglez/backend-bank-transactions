package com.rjglez.backend.bank.transactions.domain.utils;

import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataUtils {

    public static NewTransactionCommand createTransactionCommand() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return NewTransactionCommand.builder()
                                    .reference(UUID.randomUUID().toString())
                                    .accountIban("ES5220951741879861123899")
                                    .date(formatter.format(new Date()))
                                    .amount(52.79)
                                    .fee(3.50)
                                    .description("Payment in market")
                                    .build();
    }

    public static AccountEntity mockAccountEntity() {

        NewTransactionCommand newTransactionCommand = createTransactionCommand();

        AccountEntity accountEntity = AccountEntity.builder()
                                                   .id("ES3930294948393")
                                                   .balance(300.65)
                                                   .build();

        TransactionEntity transactionEntity1 = TransactionEntity.builder()
                                                                .id(newTransactionCommand.getReference())
                                                                .date(newTransactionCommand.getDate())
                                                                .amount(newTransactionCommand.getAmount())
                                                                .fee(newTransactionCommand.getFee())
                                                                .description(newTransactionCommand.getDescription())
                                                                .account(accountEntity)
                                                                .build();

        TransactionEntity transactionEntity2 = TransactionEntity.builder()
                                                                .id(UUID.randomUUID().toString())
                                                                .date(newTransactionCommand.getDate())
                                                                .amount(63.80)
                                                                .fee(newTransactionCommand.getFee())
                                                                .description(newTransactionCommand.getDescription())
                                                                .account(accountEntity)
                                                                .build();

        TransactionEntity transactionEntity3 = TransactionEntity.builder()
                                                                .id(UUID.randomUUID().toString())
                                                                .date(newTransactionCommand.getDate())
                                                                .amount(12.46)
                                                                .fee(newTransactionCommand.getFee())
                                                                .description(newTransactionCommand.getDescription())
                                                                .account(accountEntity)
                                                                .build();

        List<TransactionEntity> transactionsList = new ArrayList<>();
        transactionsList.add(transactionEntity1);
        transactionsList.add(transactionEntity2);
        transactionsList.add(transactionEntity3);

        accountEntity.setTransactions(transactionsList);

        return accountEntity;
    }

    public static NewTransactionCommand createNewTransactionCommand(double amount) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return NewTransactionCommand.builder()
                                    .reference(UUID.randomUUID().toString())
                                    .accountIban("ES5220951741879861123899")
                                    .date(formatter.format(new Date()))
                                    .amount(amount)
                                    .fee(3.50)
                                    .description("Payment in market")
                                    .build();
    }
}
