package com.rjglez.backend.bank.transactions.infrastructure.adapter.repository;

import com.rjglez.backend.bank.transactions.application.command.TransactionStatusFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionStatusResponse;
import com.rjglez.backend.bank.transactions.domain.exception.AccountDoesNotExistException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public List<TransactionEntity> findAll() {

        log.info("All transactions will be got from DB");

        return transactionJpaRepository.findAll();
    }

    @Override
    public Optional<TransactionEntity> find(String reference) {

        log.info("Transaction with reference {} will be got from DB", reference);

        return transactionJpaRepository.findById(reference);

    }
}
