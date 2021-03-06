package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.rjglez.backend.bank.transactions.application.command.TransactionStatusFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionStatusResponse;
import com.rjglez.backend.bank.transactions.application.use_case.SearchTransactionStatusUseCase;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@Slf4j
@RequestMapping(value = {"/transaction-status"})
@RequiredArgsConstructor
public class SearchTransactionStatusController {

    private final SearchTransactionStatusUseCase searchTransactionStatusUseCase;

    @ApiOperation(value = "Get a transaction status stored in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transaction status got successfully from the system"),
            @ApiResponse(code = 400, message = "Reference is missing or channel provided is not correct"),
            @ApiResponse(code = 404, message = "Account does not exist")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TransactionStatusResponse> getTransaction(@ApiParam(name = "reference", value = "Transaction reference", example = "7ade4b27-40b5-4080-bc28-dbb13ef54c50")
                                                                    @RequestParam String reference,
                                                                    @ApiParam(name = "channel", value = "Transaction channel [CLIENT, ATM, INTERNAL]", example = "INTERNAL")
                                                                    @RequestParam(required = false) String channel) throws ParseException {

        log.info("Executing search transaction status endpoint");

        TransactionStatusFinderCommand transactionStatusFinderCommand = TransactionStatusFinderCommand.of(reference, channel);

        TransactionStatusResponse transactionStatusResponse = searchTransactionStatusUseCase.find(transactionStatusFinderCommand);

        log.info("Transaction status got successfully: {}", transactionStatusResponse);

        return ResponseEntity.ok().body(transactionStatusResponse);

    }

}
