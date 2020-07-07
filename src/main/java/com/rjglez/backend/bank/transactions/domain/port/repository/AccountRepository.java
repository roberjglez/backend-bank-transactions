package com.rjglez.backend.bank.transactions.domain.port.repository;

import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;

public interface AccountRepository {

    void save(AccountEntity accountEntity);

    AccountEntity find(String accountIban);

}
