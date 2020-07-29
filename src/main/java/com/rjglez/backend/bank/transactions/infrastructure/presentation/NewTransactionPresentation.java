package com.rjglez.backend.bank.transactions.infrastructure.presentation;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "NewTransactionPresentation", description = "New transaction information")
public class NewTransactionPresentation {

    @ApiModelProperty(notes = "Transaction reference", example = "7ade4b27-40b5-4080-bc28-dbb13ef54c50", dataType = "String", position = 1)
    private String reference;

    @NotNull(message = "Account iban is mandatory")
    @NotBlank(message = "Account iban can not be null")
    @ApiModelProperty(required = true, notes = "Account iban related to the transaction", example = "ES3930294948393", dataType = "String", position = 2)
    private String accountIban;

    @ApiModelProperty(notes = "Transaction date", example = "2019-07-16T16:55:42.000Z", dataType = "String", position = 3)
    private String date;

    @NotNull(message = "Amount is mandatory")
    @ApiModelProperty(required = true, notes = "Transaction amount", example = "42.33", dataType = "double", position = 4)
    private Double amount;

    @ApiModelProperty(notes = "Transaction fee", example = "3.50", dataType = "String", position = 5)
    private Double fee;

    @ApiModelProperty(notes = "Transaction description", example = "Payment in restaurant", dataType = "String", position = 6)
    private String description;
}
