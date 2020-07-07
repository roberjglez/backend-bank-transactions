package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.domain.exception.AccountDoesNotExistException;
import com.rjglez.backend.bank.transactions.domain.exception.InsufficientBalanceException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateTransactionUseCaseTest {

    @Mock
    AccountRepository accountRepository;
    private CreateTransactionUseCase createTransactionUseCase;

    @Before
    public void setUp() {

        this.createTransactionUseCase = new CreateTransactionUseCase(accountRepository);
    }

    @Test
    public void shouldAddMoneyToAccountAndCreateTransaction() {

        // GIVEN
        double                amount                = 50.79;
        NewTransactionCommand newTransactionCommand = createTransactionCommand(amount);

        double        balance       = 200.60;
        AccountEntity accountEntity = mockAccountEntity(newTransactionCommand, balance);
        Mockito.when(accountRepository.find(newTransactionCommand.getAccountIban())).thenReturn(accountEntity);

        // WHEN
        createTransactionUseCase.create(newTransactionCommand);

        // THEN
        verify(accountRepository, times(1)).find(newTransactionCommand.getAccountIban());
        verify(accountRepository, times(1)).save(isA(AccountEntity.class));
    }

    @Test
    public void shouldDeductMoneyFromAccountAndCreateTransaction() {

        // GIVEN
        double                amount                = -50.79;
        NewTransactionCommand newTransactionCommand = createTransactionCommand(amount);

        double        balance       = 200.60;
        AccountEntity accountEntity = mockAccountEntity(newTransactionCommand, balance);
        Mockito.when(accountRepository.find(newTransactionCommand.getAccountIban())).thenReturn(accountEntity);

        // WHEN
        createTransactionUseCase.create(newTransactionCommand);

        // THEN
        verify(accountRepository, times(1)).find(newTransactionCommand.getAccountIban());
        verify(accountRepository, times(1)).save(isA(AccountEntity.class));
    }

    @Test
    public void shouldThrowAccountDoesNotExistException() {

        Assertions.assertThrows(AccountDoesNotExistException.class, () -> {

            // GIVEN
            double                amount                = 50.79;
            NewTransactionCommand newTransactionCommand = createTransactionCommand(amount);

            Mockito.when(accountRepository.find(newTransactionCommand.getAccountIban())).thenReturn(null);

            // WHEN
            createTransactionUseCase.create(newTransactionCommand);

        });
    }

    @Test
    public void shouldThrowInsufficientBalanceException() {

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {

            // GIVEN
            double                amount                = -40.78;
            NewTransactionCommand newTransactionCommand = createTransactionCommand(amount);

            double        insufficientBalance = 20.60;
            AccountEntity accountEntity       = mockAccountEntity(newTransactionCommand, insufficientBalance);
            Mockito.when(accountRepository.find(newTransactionCommand.getAccountIban())).thenReturn(accountEntity);

            // WHEN
            createTransactionUseCase.create(newTransactionCommand);

        });
    }

    private AccountEntity mockAccountEntity(NewTransactionCommand newTransactionCommand, double balance) {

        TransactionEntity transactionEntity = TransactionEntity.builder()
                                                               .id(newTransactionCommand.getReference())
                                                               .date(newTransactionCommand.getDate())
                                                               .amount(newTransactionCommand.getAmount())
                                                               .fee(newTransactionCommand.getFee())
                                                               .description(newTransactionCommand.getDescription())
                                                               .build();

        List<TransactionEntity> transactionsList = new ArrayList<>();
        transactionsList.add(transactionEntity);

        return AccountEntity.builder()
                            .id("ES3930294948393")
                            .balance(balance)
                            .transactions(transactionsList)
                            .build();
    }

    private NewTransactionCommand createTransactionCommand(double amount) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return NewTransactionCommand.builder()
                                    .reference(UUID.randomUUID().toString())
                                    .accountIban("ES5220951741879861123899")
                                    .date(formatter.format(new Date()))
                                    .amount(amount)
                                    .fee(3.50)
                                    .description("Payment in market")
                                    .build();
    }

}
