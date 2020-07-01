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
public class TransactionCommand {

    private String reference;
    private String accountIban;
    private String date;
    private double amount;
    private double fee;
    private String description;

    public static TransactionCommand of(TransactionPresentation transactionPresentation) {
        TransactionCommand transactionCommand = new TransactionCommand();
        try {
            BeanUtils.copyProperties(transactionPresentation, transactionCommand);
        } catch (Exception e) {
            throw new RuntimeException("Error creating TransactionCommand from TransactionPresentation", e);
        }
        return transactionCommand;
    }
}
