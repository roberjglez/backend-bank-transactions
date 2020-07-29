package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.application.use_case.CreateTransactionUseCase;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewTransactionPresentation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = {"/transaction"})
@RequiredArgsConstructor
public class CreateTransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    @ApiOperation(value = "Store a new transaction in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful creation of the new transaction"),
            @ApiResponse(code = 400, message = "Mandatory body parameter is missing"),
            @ApiResponse(code = 404, message = "Account does not exist")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTransaction(@ApiParam(name = "newTransactionPresentation", value = "The information of the new transaction", required = true)
                                                  @Valid @RequestBody NewTransactionPresentation newTransactionPresentation) {

        log.info("Executing create transaction endpoint with newTransactionPresentation: {}", newTransactionPresentation);

        NewTransactionCommand newTransactionCommand = NewTransactionCommand.of(newTransactionPresentation);

        createTransactionUseCase.create(newTransactionCommand);

        log.info("Transaction created successfully");

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
