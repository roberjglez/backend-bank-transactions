package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.rjglez.backend.bank.transactions.application.command.NewAccountCommand;
import com.rjglez.backend.bank.transactions.application.use_case.CreateAccountUseCase;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewAccountPresentation;
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
@RequestMapping(value = {"/account"})
@RequiredArgsConstructor
public class CreateAccountController {

    private final CreateAccountUseCase createAccountUseCase;

    @ApiOperation(value = "Store a new account in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful creation of the new account"),
            @ApiResponse(code = 400, message = "Mandatory body parameter is missing"),
            @ApiResponse(code = 404, message = "Account already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAccount(@ApiParam(name = "newAccountPresentation", value = "The information of the new account", required = true)
                                              @Valid @RequestBody NewAccountPresentation newAccountPresentation) {

        log.info("Executing create account endpoint with newTransactionPresentation: {}", newAccountPresentation);

        NewAccountCommand newAccountCommand = NewAccountCommand.of(newAccountPresentation);

        createAccountUseCase.create(newAccountCommand);

        log.info("Account created successfully");

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
