package com.rjglez.backend.bank.transactions.application.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "TransactionResponse", description = "Transaction response information")
public class TransactionResponse {

    @ApiModelProperty(notes = "Transaction reference", example = "7ade4b27-40b5-4080-bc28-dbb13ef54c50", dataType = "String")
    private String reference;

    @ApiModelProperty(notes = "Account iban related to the transaction", example = "ES3930294948393", dataType = "String")
    private String accountIban;

    @ApiModelProperty(notes = "Transaction date", example = "2019-07-16T16:55:42.000Z", dataType = "String")
    private String date;

    @ApiModelProperty(notes = "Transaction amount", example = "42.33", dataType = "double")
    private double amount;

    @ApiModelProperty(notes = "Transaction fee", example = "3.50", dataType = "String")
    private double fee;

    @ApiModelProperty(notes = "Transaction description", example = "Payment in restaurant", dataType = "String")
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
