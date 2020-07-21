package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.TransactionFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionResponse;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.AccountRepository;
import com.rjglez.backend.bank.transactions.domain.port.repository.TransactionRepository;
import com.rjglez.backend.bank.transactions.domain.utils.DataUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetTransactionUseCaseTest {

    private GetTransactionUseCase getTransactionUseCase;

    @Mock
    AccountRepository     accountRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Before
    public void setUp() {

        this.getTransactionUseCase = new GetTransactionUseCase(accountRepository, transactionRepository);
    }

    @Test
    public void shouldReturnAllTransactionsFromDBWithoutSortingThem() {

        // GIVEN
        TransactionFinderCommand transactionFinderCommand = TransactionFinderCommand.builder()
                                                                                    .accountIban(null)
                                                                                    .sorting(null)
                                                                                    .build();

        List<TransactionEntity> transactionEntityList = createRandomTransactionsList();
        Mockito.when(transactionRepository.findAll()).thenReturn(transactionEntityList);

        // WHEN
        List<TransactionResponse> transactionResponseList = getTransactionUseCase.find(transactionFinderCommand);

        // THEN
        verify(transactionRepository, times(1)).findAll();

        Assertions.assertThat(transactionResponseList.size()).isEqualTo(transactionEntityList.size());

        Assertions.assertThat(transactionResponseList)
                  .containsExactlyInAnyOrderElementsOf(transactionEntityList.stream()
                                                                            .map(TransactionResponse::of)
                                                                            .collect(Collectors.toList()));

    }

    @Test
    public void shouldReturnAllTransactionsFromDBSortingThemAscending() {

        // GIVEN
        TransactionFinderCommand transactionFinderCommand = TransactionFinderCommand.builder()
                                                                                    .accountIban(null)
                                                                                    .sorting("ascending")
                                                                                    .build();

        List<TransactionEntity> transactionEntityList = createRandomTransactionsList();
        Mockito.when(transactionRepository.findAll()).thenReturn(transactionEntityList);

        // WHEN
        List<TransactionResponse> transactionResponseList = getTransactionUseCase.find(transactionFinderCommand);

        // THEN
        verify(transactionRepository, times(1)).findAll();

        Assertions.assertThat(transactionResponseList.size()).isEqualTo(transactionEntityList.size());

        Assertions.assertThat(transactionResponseList)
                  .containsExactlyInAnyOrderElementsOf(transactionEntityList.stream()
                                                                            .sorted(Comparator.comparingDouble(TransactionEntity::getAmount))
                                                                            .map(TransactionResponse::of)
                                                                            .collect(Collectors.toList()));

    }

    @Test
    public void shouldReturnAllTransactionsFromDBSortingThemDescending() {

        // GIVEN
        TransactionFinderCommand transactionFinderCommand = TransactionFinderCommand.builder()
                                                                                    .accountIban(null)
                                                                                    .sorting("descending")
                                                                                    .build();

        List<TransactionEntity> transactionEntityList = createRandomTransactionsList();
        Mockito.when(transactionRepository.findAll()).thenReturn(transactionEntityList);

        // WHEN
        List<TransactionResponse> transactionResponseList = getTransactionUseCase.find(transactionFinderCommand);

        // THEN
        verify(transactionRepository, times(1)).findAll();

        Assertions.assertThat(transactionResponseList.size()).isEqualTo(transactionEntityList.size());

        Assertions.assertThat(transactionResponseList)
                  .containsExactlyInAnyOrderElementsOf(transactionEntityList.stream()
                                                                            .sorted(Comparator.comparingDouble(TransactionEntity::getAmount).reversed())
                                                                            .map(TransactionResponse::of)
                                                                            .collect(Collectors.toList()));

    }

    @Test
    public void shouldReturnTransactionsFromSpecificAccountWithoutSortingThem() {

        // GIVEN
        TransactionFinderCommand transactionFinderCommand = TransactionFinderCommand.builder()
                                                                                    .accountIban("ES3930294948393")
                                                                                    .sorting(null)
                                                                                    .build();

        AccountEntity accountEntity = DataUtils.mockAccountEntity();
        Mockito.when(accountRepository.find(accountEntity.getId())).thenReturn(accountEntity);

        // WHEN
        List<TransactionResponse> transactionResponseList = getTransactionUseCase.find(transactionFinderCommand);

        // THEN
        List<TransactionEntity> transactionEntityList = accountEntity.getTransactions();

        Assertions.assertThat(transactionResponseList.size()).isEqualTo(transactionEntityList.size());

        Assertions.assertThat(transactionResponseList)
                  .containsExactlyInAnyOrderElementsOf(transactionEntityList.stream()
                                                                            .map(TransactionResponse::of)
                                                                            .collect(Collectors.toList()));

    }

    @Test
    public void shouldReturnTransactionsFromSpecificAccountSortingThemAscending() {

        // GIVEN
        TransactionFinderCommand transactionFinderCommand = TransactionFinderCommand.builder()
                                                                                    .accountIban("ES3930294948393")
                                                                                    .sorting("ascending")
                                                                                    .build();

        AccountEntity accountEntity = DataUtils.mockAccountEntity();
        Mockito.when(accountRepository.find(accountEntity.getId())).thenReturn(accountEntity);

        // WHEN
        List<TransactionResponse> transactionResponseList = getTransactionUseCase.find(transactionFinderCommand);

        // THEN
        List<TransactionEntity> transactionEntityList = accountEntity.getTransactions();

        Assertions.assertThat(transactionResponseList.size()).isEqualTo(transactionEntityList.size());

        Assertions.assertThat(transactionResponseList)
                  .containsExactlyInAnyOrderElementsOf(transactionEntityList.stream()
                                                                            .sorted(Comparator.comparingDouble(TransactionEntity::getAmount))
                                                                            .map(TransactionResponse::of)
                                                                            .collect(Collectors.toList()));

    }

    @Test
    public void shouldReturnTransactionsFromSpecificAccountSortingThemDescending() {

        // GIVEN
        TransactionFinderCommand transactionFinderCommand = TransactionFinderCommand.builder()
                                                                                    .accountIban("ES3930294948393")
                                                                                    .sorting("descending")
                                                                                    .build();

        AccountEntity accountEntity = DataUtils.mockAccountEntity();
        Mockito.when(accountRepository.find(accountEntity.getId())).thenReturn(accountEntity);

        // WHEN
        List<TransactionResponse> transactionResponseList = getTransactionUseCase.find(transactionFinderCommand);

        // THEN
        List<TransactionEntity> transactionEntityList = accountEntity.getTransactions();

        Assertions.assertThat(transactionResponseList.size()).isEqualTo(transactionEntityList.size());

        Assertions.assertThat(transactionResponseList)
                  .containsExactlyInAnyOrderElementsOf(transactionEntityList.stream()
                                                                            .sorted(Comparator.comparingDouble(TransactionEntity::getAmount).reversed())
                                                                            .map(TransactionResponse::of)
                                                                            .collect(Collectors.toList()));

    }

    private List<TransactionEntity> createRandomTransactionsList() {

        TransactionEntity transactionEntity1 = TransactionEntity.builder()
                                                                .id(UUID.randomUUID().toString())
                                                                .date(DataUtils.FORMATTER.format(new Date()))
                                                                .amount(50.70)
                                                                .fee(0.00)
                                                                .description("Payment in restaurant")
                                                                .account(AccountEntity.builder()
                                                                                      .id(UUID.randomUUID().toString())
                                                                                      .build())
                                                                .build();

        TransactionEntity transactionEntity2 = TransactionEntity.builder()
                                                                .id(UUID.randomUUID().toString())
                                                                .date(DataUtils.FORMATTER.format(new Date()))
                                                                .amount(63.80)
                                                                .fee(3.76)
                                                                .description("Payment in shop")
                                                                .account(AccountEntity.builder()
                                                                                      .id(UUID.randomUUID().toString())
                                                                                      .build())
                                                                .build();

        TransactionEntity transactionEntity3 = TransactionEntity.builder()
                                                                .id(UUID.randomUUID().toString())
                                                                .date(DataUtils.FORMATTER.format(new Date()))
                                                                .amount(12.46)
                                                                .fee(1.40)
                                                                .description("Payment in restaurant")
                                                                .account(AccountEntity.builder()
                                                                                      .id(UUID.randomUUID().toString())
                                                                                      .build())
                                                                .build();

        List<TransactionEntity> transactionsList = new ArrayList<>();
        transactionsList.add(transactionEntity1);
        transactionsList.add(transactionEntity2);
        transactionsList.add(transactionEntity3);

        return transactionsList;
    }


}
