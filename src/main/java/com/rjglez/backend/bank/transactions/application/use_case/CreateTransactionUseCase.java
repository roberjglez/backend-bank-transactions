package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.domain.exception.InsufficientBalanceException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTransactionUseCase {

    private final AccountRepository accountRepository;

    @Transactional
    public void create(NewTransactionCommand newTransactionCommand) {

        log.info("Process in CreateTransactionUseCase starts");

        TransactionEntity transactionEntity = TransactionEntity.of(newTransactionCommand);
        transactionEntity.checkParameters();

        processTransaction(transactionEntity, newTransactionCommand.getAccountIban());

        log.info("Process in CreateTransactionUseCase ends");
    }

    private void processTransaction(TransactionEntity transactionEntity, String accountIban) {

        AccountEntity account = accountRepository.find(accountIban);

        double amountToProcess = transactionEntity.getAmountToProcess();

        if (account.isTransactionAllowed(amountToProcess)) {
            account.addTransaction(transactionEntity);
            account.modifyBalance(amountToProcess);
            accountRepository.save(account);
        } else {
            log.error("Insufficient balance in account. Current balance in account is {} and the amount to process is {}", account.getBalance(), amountToProcess);
            throw new InsufficientBalanceException(accountIban);
        }
    }
}
