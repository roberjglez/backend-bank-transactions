package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.infrastructure.presentation.TransactionPresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewTransactionCommand {

    private String reference;
    private String accountIban;
    private String date;
    private double amount;
    private double fee;
    private String description;

    public static NewTransactionCommand of(TransactionPresentation transactionPresentation) {
        NewTransactionCommand newTransactionCommand = new NewTransactionCommand();
        try {
            BeanUtils.copyProperties(transactionPresentation, newTransactionCommand);
        } catch (Exception e) {
            throw new RuntimeException("Error creating NewTransactionCommand from TransactionPresentation", e);
        }
        return newTransactionCommand;
    }
}
