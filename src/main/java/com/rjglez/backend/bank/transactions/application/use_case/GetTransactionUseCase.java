package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.TransactionFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionResponse;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.AccountRepository;
import com.rjglez.backend.bank.transactions.domain.port.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class GetTransactionUseCase {

    private static final String SORT_ASCENDING  = "ascending";
    private static final String SORT_DESCENDING = "descending";

    private final AccountRepository     accountRepository;
    private final TransactionRepository transactionRepository;

    public List<TransactionResponse> find(TransactionFinderCommand transactionFinderCommand) {

        log.info("Process in GetTransactionUseCase starts");

        List<TransactionResponse> transactionsList = getTransactions(transactionFinderCommand);
        return !Objects.isNull(transactionFinderCommand.getSorting()) ? sortTransactions(transactionsList, transactionFinderCommand) : transactionsList;

    }

    private List<TransactionResponse> getTransactions(TransactionFinderCommand transactionFinderCommand) {

        List<TransactionEntity> transactionsEntitiesList = new ArrayList<>();

        if (Objects.isNull(transactionFinderCommand.getAccountIban())) {
            log.info("Account IBAN not provided, DB will return all transactions");
            transactionsEntitiesList = transactionRepository.findAll();
        } else {
            AccountEntity account = accountRepository.find(transactionFinderCommand.getAccountIban());
            transactionsEntitiesList = account.getTransactions();
            log.info("Account with IBAN {} has {} transactions", transactionFinderCommand.getAccountIban(), transactionsEntitiesList.size());
        }

        return transactionsEntitiesList.stream()
                                       .map(TransactionResponse::of)
                                       .collect(Collectors.toList());
    }

    private List<TransactionResponse> sortTransactions(List<TransactionResponse> transactionsList, TransactionFinderCommand transactionFinderCommand) {

        switch (transactionFinderCommand.getSorting()) {
            case (SORT_ASCENDING):
                log.info("Sort type is ascending");
                return transactionsList.stream()
                                       .sorted(Comparator.comparingDouble(TransactionResponse::getAmount))
                                       .collect(Collectors.toList());
            case (SORT_DESCENDING):
                log.info("Sort type is descending");
                return transactionsList.stream()
                                       .sorted(Comparator.comparingDouble(TransactionResponse::getAmount).reversed())
                                       .collect(Collectors.toList());
            default:
                return transactionsList;
        }
    }

}
