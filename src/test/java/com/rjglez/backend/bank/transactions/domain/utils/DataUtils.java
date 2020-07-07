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

        TransactionEntity transactionEntity = TransactionEntity.builder()
                                                               .id(newTransactionCommand.getReference())
                                                               .date(newTransactionCommand.getDate())
                                                               .amount(newTransactionCommand.getAmount())
                                                               .fee(newTransactionCommand.getFee())
                                                               .description(newTransactionCommand.getDescription())
                                                               .build();

        List<TransactionEntity> transactionsList = new ArrayList<>();
        transactionsList.add(transactionEntity);

        return AccountEntity.builder()
                            .id("ES3930294948393")
                            .balance(300.65)
                            .transactions(transactionsList)
                            .build();
    }
}
