package com.rjglez.backend.bank.transactions.infrastructure.presentation;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SearchTransactionStatusPresentation {

    @NotNull(message = "Reference is mandatory")
    private String reference;
    private String channel;
}
