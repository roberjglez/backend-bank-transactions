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

    @ApiModelProperty(notes = "Transaction reference", example = "7ade4b27-40b5-4080-bc28-dbb13ef54c50", dataType = "String")
    private String reference;

    @NotBlank(message = "Account iban is mandatory")
    @ApiModelProperty(required = true, notes = "Account iban related to the transaction", example = "ES3930294948393", dataType = "String")
    private String accountIban;

    @ApiModelProperty(notes = "Transaction date", example = "2019-07-16T16:55:42.000Z", dataType = "String")
    private String date;

    @NotNull(message = "Amount is mandatory")
    @ApiModelProperty(required = true, notes = "Transaction amount", example = "42.33", dataType = "double")
    private Double amount;

    @ApiModelProperty(notes = "Transaction fee", example = "3.50", dataType = "String")
    private Double fee;

    @ApiModelProperty(notes = "Transaction description", example = "Payment in restaurant", dataType = "String")
    private String description;
}
