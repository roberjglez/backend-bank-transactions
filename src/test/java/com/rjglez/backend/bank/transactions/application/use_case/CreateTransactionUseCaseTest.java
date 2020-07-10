package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.domain.exception.InsufficientBalanceException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.AccountRepository;
import com.rjglez.backend.bank.transactions.domain.utils.DataUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateTransactionUseCaseTest {

    private CreateTransactionUseCase createTransactionUseCase;

    @Mock
    AccountRepository accountRepository;

    @Before
    public void setUp() {

        this.createTransactionUseCase = new CreateTransactionUseCase(accountRepository);
    }

    @Test
    public void shouldAddMoneyToAccountAndCreateTransaction() {

        // GIVEN
        double                amount                = 50.79;
        NewTransactionCommand newTransactionCommand = DataUtils.createNewTransactionCommand(amount);

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
        NewTransactionCommand newTransactionCommand = DataUtils.createNewTransactionCommand(amount);

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
    public void shouldThrowInsufficientBalanceException() {

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {

            // GIVEN
            double                amount                = -40.78;
            NewTransactionCommand newTransactionCommand = DataUtils.createNewTransactionCommand(amount);

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

}
