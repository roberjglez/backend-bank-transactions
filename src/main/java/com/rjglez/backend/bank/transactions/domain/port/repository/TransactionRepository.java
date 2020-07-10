package com.rjglez.backend.bank.transactions.domain.port.repository;

import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;

import java.util.List;

public interface TransactionRepository {

    List<TransactionEntity> findAll();
}
