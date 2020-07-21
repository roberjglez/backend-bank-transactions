package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.TransactionStatusFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionStatusResponse;
import com.rjglez.backend.bank.transactions.domain.exception.AccountDoesNotExistException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class SearchTransactionStatusUseCase {

    private final TransactionRepository transactionRepository;

    public TransactionStatusResponse find(TransactionStatusFinderCommand transactionStatusFinderCommand) throws ParseException {

        log.info("Process in GetTransactionUseCase starts");

        return getTransaction(transactionStatusFinderCommand);
    }

    private TransactionStatusResponse getTransaction(TransactionStatusFinderCommand transactionStatusFinderCommand) throws ParseException {

        String                reference = transactionStatusFinderCommand.getReference();

        TransactionEntity transaction = transactionRepository.find(reference).orElse(null);

        if (!Objects.isNull(transaction)) {
            log.info("Transaction with reference {} got from DB: {}", reference, transaction);
            return TransactionStatusResponse.of(transaction, transactionStatusFinderCommand.getChannel());
        } else {
            log.error("Transaction with reference {} is not stored in the system", reference);
            return TransactionStatusResponse.ofTransactionNotStored(reference);
        }
    }

}
