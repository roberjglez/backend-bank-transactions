package com.rjglez.backend.bank.transactions.infrastructure.web.validation;

import com.google.common.base.Preconditions;
import com.rjglez.backend.bank.transactions.infrastructure.exception.ParameterMandatoryNotFoundException;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewTransactionPresentation;

import java.util.Objects;

public class ValidationUtils {

    public static void validateFields(NewTransactionPresentation newTransactionPresentation) {
        try {
            Preconditions.checkArgument(!Objects.isNull(newTransactionPresentation.getAccountIban()), "Account iban is mandatory");
            Preconditions.checkArgument(!Objects.isNull(newTransactionPresentation.getAmount()), "Amount is mandatory");
        } catch (Exception e) {
            throw new ParameterMandatoryNotFoundException("account_iban or amount");
        }
    }
}
