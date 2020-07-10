package com.rjglez.backend.bank.transactions.application.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransactionResponse {

    private String reference;
    private String accountIban;
    private String date;
    private double amount;
    private double fee;
    private String description;

    public static TransactionResponse of(TransactionEntity transactionEntity) {

        return TransactionResponse.builder()
                                  .reference(transactionEntity.getId())
                                  .accountIban(transactionEntity.getAccount().getId())
                                  .date(transactionEntity.getDate())
                                  .amount(transactionEntity.getAmount())
                                  .fee(transactionEntity.getFee())
                                  .description(transactionEntity.getDescription())
                                  .build();
    }
}
