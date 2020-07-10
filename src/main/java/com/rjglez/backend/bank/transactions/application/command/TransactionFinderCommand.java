package com.rjglez.backend.bank.transactions.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionFinderCommand {

    private String accountIban;
    private String sorting;

    public static TransactionFinderCommand of(String accountIban, String sorting) {

        return TransactionFinderCommand.builder()
                                       .accountIban(accountIban)
                                       .sorting(sorting)
                                       .build();

    }

}
