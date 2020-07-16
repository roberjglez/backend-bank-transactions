package com.rjglez.backend.bank.transactions.infrastructure.presentation;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NewTransactionPresentation {

    private String reference;
    @NotNull(message = "Account iban is mandatory")
    private String accountIban;
    private String date;
    @NotNull(message = "Amount is mandatory")
    private double amount;
    private double fee;
    private String description;
}
