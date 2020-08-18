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
@ApiModel(value = "NewAccountPresentation", description = "New account information")
public class NewAccountPresentation {

    @NotBlank(message = "Account iban is mandatory")
    @ApiModelProperty(notes = "Iban account", example = "ES9020803644194395113658", dataType = "String")
    private String iban;

    @NotNull(message = "Balance is mandatory")
    @ApiModelProperty(required = true, notes = "Account balance", example = "42.33", dataType = "double")
    private Double balance;

}
