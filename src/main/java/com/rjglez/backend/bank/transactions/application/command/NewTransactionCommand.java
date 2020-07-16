package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewTransactionPresentation;
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

    public static NewTransactionCommand of(NewTransactionPresentation newTransactionPresentation) {
        NewTransactionCommand newTransactionCommand = new NewTransactionCommand();
        try {
            BeanUtils.copyProperties(newTransactionPresentation, newTransactionCommand);
        } catch (Exception e) {
            throw new RuntimeException("Error creating NewTransactionCommand from NewTransactionPresentation", e);
        }
        return newTransactionCommand;
    }
}
