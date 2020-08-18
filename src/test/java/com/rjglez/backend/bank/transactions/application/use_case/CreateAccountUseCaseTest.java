package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.NewAccountCommand;
import com.rjglez.backend.bank.transactions.domain.exception.AccountAlreadyExistsException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccountUseCaseTest {

    private CreateAccountUseCase createAccountUseCase;

    @Mock
    AccountRepository accountRepository;

    @Before
    public void setUp() {

        this.createAccountUseCase = new CreateAccountUseCase(accountRepository);
    }

    @Test
    public void shouldCreateNewAccount() {

        // GIVEN
        NewAccountCommand newAccountCommand = NewAccountCommand.builder()
                                                               .iban("ES3930294948393")
                                                               .balance(200.45)
                                                               .build();

        Mockito.when(accountRepository.find(newAccountCommand.getIban())).thenReturn(Optional.empty());

        // WHEN
        createAccountUseCase.create(newAccountCommand);

        // THEN
        verify(accountRepository, times(1)).find(newAccountCommand.getIban());
        verify(accountRepository, times(1)).save(isA(AccountEntity.class));
    }

    @Test
    public void shouldThrowAccountAlreadyExistsException() {

        Assertions.assertThrows(AccountAlreadyExistsException.class, () -> {

            // GIVEN
            NewAccountCommand newAccountCommand = NewAccountCommand.builder()
                                                                   .iban("ES3930294948393")
                                                                   .balance(200.45)
                                                                   .build();

            AccountEntity accountEntity = AccountEntity.builder()
                                                       .id("ES3930294948393")
                                                       .balance(3000.45)
                                                       .build();
            Mockito.when(accountRepository.find(newAccountCommand.getIban())).thenReturn(Optional.of(accountEntity));

            // WHEN
            createAccountUseCase.create(newAccountCommand);

        });
    }
}
