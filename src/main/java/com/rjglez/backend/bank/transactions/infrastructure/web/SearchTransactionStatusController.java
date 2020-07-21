package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.rjglez.backend.bank.transactions.application.command.TransactionStatusFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionStatusResponse;
import com.rjglez.backend.bank.transactions.application.use_case.SearchTransactionStatusUseCase;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.SearchTransactionStatusPresentation;
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
    public ResponseEntity<TransactionStatusResponse> getTransaction(@Valid @RequestBody SearchTransactionStatusPresentation searchTransactionStatusPresentation) throws ParseException {

        log.info("Executing search transaction status endpoint");

        TransactionStatusFinderCommand transactionStatusFinderCommand = TransactionStatusFinderCommand.of(searchTransactionStatusPresentation);

        TransactionStatusResponse transactionsStatusResponse = searchTransactionStatusUseCase.find(transactionStatusFinderCommand);

        log.info("Transaction status got successfully: {}", transactionsStatusResponse);

        return ResponseEntity.ok().body(transactionsStatusResponse);

    }

}
