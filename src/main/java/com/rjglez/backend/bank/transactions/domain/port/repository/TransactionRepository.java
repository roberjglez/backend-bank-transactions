package com.rjglez.backend.bank.transactions.domain.port.repository;

import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;

public interface TransactionRepository {

    void save(TransactionEntity transactionEntity);
}
