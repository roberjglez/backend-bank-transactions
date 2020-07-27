package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.rjglez.backend.bank.transactions.application.command.TransactionStatusFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionStatusResponse;
import com.rjglez.backend.bank.transactions.application.use_case.SearchTransactionStatusUseCase;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.SearchTransactionStatusPresentation;
import com.rjglez.backend.bank.transactions.infrastructure.web.validation.ValidationUtils;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@Slf4j
@RequestMapping(value = {"/transaction-status"})
@RequiredArgsConstructor
public class SearchTransactionStatusController {

    private final SearchTransactionStatusUseCase searchTransactionStatusUseCase;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TransactionStatusResponse> getTransaction(@ApiParam(name = "searchTransactionStatusPresentation", value = "Reference and channel of the desired transaction", required = true)
                                                                    @Valid @RequestBody SearchTransactionStatusPresentation searchTransactionStatusPresentation) throws ParseException {

        ValidationUtils.validateFields(searchTransactionStatusPresentation);

        log.info("Executing search transaction status endpoint");

        TransactionStatusFinderCommand transactionStatusFinderCommand = TransactionStatusFinderCommand.of(searchTransactionStatusPresentation);

        TransactionStatusResponse transactionsStatusResponse = searchTransactionStatusUseCase.find(transactionStatusFinderCommand);

        log.info("Transaction status got successfully: {}", transactionsStatusResponse);

        return ResponseEntity.ok().body(transactionsStatusResponse);

    }

}
