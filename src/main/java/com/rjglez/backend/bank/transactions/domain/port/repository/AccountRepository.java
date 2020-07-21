package com.rjglez.backend.bank.transactions.domain.port.repository;

import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;

import java.util.Optional;

public interface AccountRepository {

    void save(AccountEntity accountEntity);

    Optional<AccountEntity> find(String accountIban);

}
