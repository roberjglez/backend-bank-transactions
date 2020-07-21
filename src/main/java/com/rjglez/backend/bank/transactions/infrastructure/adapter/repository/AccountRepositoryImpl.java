package com.rjglez.backend.bank.transactions.infrastructure.adapter.repository;

import com.rjglez.backend.bank.transactions.domain.exception.AccountDoesNotExistException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public void save(AccountEntity accountEntity) {

        log.info("Account will be saved in DB: {}", accountEntity.toString());

        accountJpaRepository.save(accountEntity);
    }

    @Override
    public Optional<AccountEntity> find(String accountIban) {

        return accountJpaRepository.findById(accountIban);
    }
}
