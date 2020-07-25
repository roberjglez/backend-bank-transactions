package com.rjglez.backend.bank.transactions.infrastructure.presentation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(value = "SearchTransactionStatusPresentation", description = "Reference and channel of the desired transaction")
public class SearchTransactionStatusPresentation {

    @NotNull(message = "Reference is mandatory")
    @ApiModelProperty(required = true, notes = "Transaction reference", example = "7ade4b27-40b5-4080-bc28-dbb13ef54c50", dataType = "String", position = 1)
    private String reference;

    @ApiModelProperty(notes = "Transaction channel [CLIENT, ATM, INTERNAL]", example = "INTERNAL", dataType = "String", position = 2)
    private String channel;
}
