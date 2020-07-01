package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.TransactionCommand;
import com.rjglez.backend.bank.transactions.domain.exception.AccountDoesNotExistException;
import com.rjglez.backend.bank.transactions.domain.exception.InsufficientBalanceException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTransactionUseCase {

    private final AccountRepository accountRepository;

    @Transactional
    public void create(TransactionCommand transactionCommand) {

        log.info("Process in CreateTransactionUseCase starts");

        TransactionEntity transactionEntity = TransactionEntity.of(transactionCommand);
        transactionEntity.checkParameters();

        processTransaction(transactionEntity, transactionCommand.getAccountIban());

        log.info("Process in CreateTransactionUseCase ends");
    }

    private void processTransaction(TransactionEntity transactionEntity, String accountIban) {

        AccountEntity account = getAccount(accountIban);

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

    private AccountEntity getAccount(String accountIban) {

        Optional<AccountEntity> account = accountRepository.find(accountIban);

        if (account.isPresent()) {
            return account.get();
        } else {
            log.error("Account with IBAN {} does not exist", accountIban);
            throw new AccountDoesNotExistException(accountIban);
        }
    }
}
