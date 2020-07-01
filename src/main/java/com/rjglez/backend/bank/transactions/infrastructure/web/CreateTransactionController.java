package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.rjglez.backend.bank.transactions.application.command.TransactionCommand;
import com.rjglez.backend.bank.transactions.application.use_case.CreateTransactionUseCase;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.TransactionPresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = {"/create-transaction"})
@RequiredArgsConstructor
public class CreateTransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionPresentation transactionPresentation) {

        log.info("Executing create-transaction endpoint with transactionPresentation: {}", transactionPresentation);

        TransactionCommand transactionCommand = TransactionCommand.of(transactionPresentation);

        createTransactionUseCase.create(transactionCommand);

        log.info("Transaction created successfully");

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
