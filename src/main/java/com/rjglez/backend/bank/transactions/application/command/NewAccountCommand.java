package com.rjglez.backend.bank.transactions.application.command;

import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewAccountPresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewAccountCommand {

    private String iban;
    private Double balance;

    public static NewAccountCommand of(NewAccountPresentation newAccountPresentation) {
        NewAccountCommand newAccountCommand = new NewAccountCommand();
        try {
            BeanUtils.copyProperties(newAccountPresentation, newAccountCommand);
        } catch (Exception e) {
            throw new RuntimeException("Error creating NewAccountCommand from NewAccountPresentation", e);
        }

        return newAccountCommand;
    }

}
