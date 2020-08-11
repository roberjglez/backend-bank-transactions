package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.application.utils.DateUtils;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewTransactionPresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewTransactionCommand {

    private String reference;
    private String accountIban;
    private String date;
    private Double amount;
    private Double fee;
    private String description;

    public static NewTransactionCommand of(NewTransactionPresentation newTransactionPresentation) {
        NewTransactionCommand newTransactionCommand = new NewTransactionCommand();
        try {
            BeanUtils.copyProperties(newTransactionPresentation, newTransactionCommand);
        } catch (Exception e) {
            throw new RuntimeException("Error creating NewTransactionCommand from NewTransactionPresentation", e);
        }

        checkParameters(newTransactionCommand);
        return newTransactionCommand;
    }

    private static void checkParameters(NewTransactionCommand newTransactionCommand) {

        newTransactionCommand.reference = Objects.isNull(newTransactionCommand.reference) || newTransactionCommand.reference.isEmpty() ? UUID.randomUUID().toString() : newTransactionCommand.reference;
        newTransactionCommand.date = Objects.isNull(newTransactionCommand.date) || newTransactionCommand.date.isEmpty() ? DateUtils.FORMATTER.format(new Date()) : newTransactionCommand.date;
    }
}
