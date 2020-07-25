package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.rjglez.backend.bank.transactions.application.command.TransactionFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionResponse;
import com.rjglez.backend.bank.transactions.application.use_case.GetTransactionUseCase;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = {"/transaction"})
@RequiredArgsConstructor
public class GetTransactionController {

    private final GetTransactionUseCase getTransactionUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<TransactionResponse>> getTransaction(@ApiParam(name = "accountIban", value = "Account iban related to the transaction", example = "ES3930294948393")
                                                                    @RequestParam(required = false) String accountIban,
                                                                    @ApiParam(name = "sorting", value = "Sorting type desired [ascending, descending]", example = "ascending")
                                                                    @RequestParam(required = false) String sorting) {

        log.info("Executing get transaction endpoint");

        TransactionFinderCommand transactionFinderCommand = TransactionFinderCommand.of(accountIban, sorting);

        List<TransactionResponse> transactionsList = getTransactionUseCase.find(transactionFinderCommand);

        log.info("Transactions list with {} values got successfully", transactionsList.size());

        return ResponseEntity.ok().body(transactionsList);

    }

}
