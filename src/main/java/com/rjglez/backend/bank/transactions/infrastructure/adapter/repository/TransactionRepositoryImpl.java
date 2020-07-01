package com.rjglez.backend.bank.transactions.infrastructure.adapter.repository;

import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public void save(TransactionEntity transactionEntity) {

        log.debug("Transaction will be saved in DB: {}", transactionEntity.toString());

        transactionJpaRepository.save(transactionEntity);
    }
}
