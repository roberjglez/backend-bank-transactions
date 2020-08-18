package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.NewAccountCommand;
import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.domain.exception.AccountAlreadyExistsException;
import com.rjglez.backend.bank.transactions.domain.exception.AccountDoesNotExistException;
import com.rjglez.backend.bank.transactions.domain.exception.InsufficientBalanceException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountRepository accountRepository;

    @Transactional
    public void create(NewAccountCommand newAccountCommand) {

        log.info("Process in CreateAccountUseCase starts");

        AccountEntity accountEntity = AccountEntity.of(newAccountCommand);

        processNewAccount(accountEntity);

        log.info("Process in CreateTransactionUseCase ends");
    }

    private void processNewAccount(AccountEntity accountEntity) {

        AccountEntity accountStored = accountRepository.find(accountEntity.getId()).orElse(null);

        if (Objects.isNull(accountStored)) {
            accountRepository.save(accountEntity);
        } else {
            log.error("Account with IBAN {} already exists in the system", accountEntity.getId());
            throw new AccountAlreadyExistsException(accountEntity.getId());
        }

    }
}
