package com.rjglez.backend.bank.transactions.infrastructure.adapter.repository;

import com.rjglez.backend.bank.transactions.domain.exception.AccountDoesNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryImplTest {

    private AccountRepositoryImpl accountRepository;

    @Mock
    AccountJpaRepository accountJpaRepository;

    @Before
    public void setUp() {

        this.accountRepository = new AccountRepositoryImpl(accountJpaRepository);
    }

    @Test
    public void shouldThrowAccountDoesNotExistException() {

        Assertions.assertThrows(AccountDoesNotExistException.class, () -> {

            // GIVEN
            String accountIban = "ES5220951741879861123899";
            Mockito.when(accountJpaRepository.findById(accountIban)).thenReturn(Optional.empty());

            // WHEN
            accountRepository.find(accountIban);

        });
    }
}
