package com.rjglez.backend.bank.transactions.application.use_case;

import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.application.command.TransactionStatusFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionStatusResponse;
import com.rjglez.backend.bank.transactions.domain.exception.ChannelNotProvidedException;
import com.rjglez.backend.bank.transactions.domain.exception.InsufficientBalanceException;
import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.port.repository.TransactionRepository;
import com.rjglez.backend.bank.transactions.domain.utils.DataUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SearchTransactionStatusUseCaseTest {

    private SearchTransactionStatusUseCase searchTransactionStatusUseCase;

    @Mock
    TransactionRepository transactionRepository;

    @Before
    public void setUp() {

        this.searchTransactionStatusUseCase = new SearchTransactionStatusUseCase(transactionRepository);
    }

    @Test
    public void shouldGetTransactionAndReturnTransactionStatusResponse() throws ParseException {

        // GIVEN
        TransactionStatusFinderCommand transactionStatusFinderCommand = TransactionStatusFinderCommand.builder()
                                                                                                      .reference("d7256d14-b464-48af-94a2-50fc99ed057c")
                                                                                                      .channel("CLIENT")
                                                                                                      .build();

        TransactionEntity transactionEntity = createTransactionEntity();
        Mockito.when(transactionRepository.find(transactionStatusFinderCommand.getReference())).thenReturn(Optional.of(transactionEntity));

        // WHEN
        TransactionStatusResponse transactionStatusResponse = searchTransactionStatusUseCase.find(transactionStatusFinderCommand);

        // THEN
        verify(transactionRepository, times(1)).find(transactionStatusFinderCommand.getReference());

        Assertions.assertThat(transactionStatusResponse.getReference()).isEqualTo(transactionStatusFinderCommand.getReference());
        Assertions.assertThat(transactionStatusResponse.getStatus()).isNotNull();
        Assertions.assertThat(transactionStatusResponse.getAmount()).isNotNull();
    }

    @Test
    public void shouldReturnTransactionStatusResponseWhenTransactionIsNotStored() throws ParseException {

        // GIVEN
        TransactionStatusFinderCommand transactionStatusFinderCommand = TransactionStatusFinderCommand.builder()
                                                                                                      .reference("d7256d14-b464-48af-94a2-50fc99ed057c")
                                                                                                      .channel("CLIENT")
                                                                                                      .build();

        Mockito.when(transactionRepository.find(transactionStatusFinderCommand.getReference())).thenReturn(Optional.empty());

        // WHEN
        TransactionStatusResponse transactionStatusResponse = searchTransactionStatusUseCase.find(transactionStatusFinderCommand);

        // THEN
        verify(transactionRepository, times(1)).find(transactionStatusFinderCommand.getReference());

        Assertions.assertThat(transactionStatusResponse.getReference()).isEqualTo(transactionStatusFinderCommand.getReference());
        Assertions.assertThat(transactionStatusResponse.getStatus()).isEqualTo(TransactionStatusResponse.TransactionStatus.INVALID);
    }

    private TransactionEntity createTransactionEntity() {
        return TransactionEntity.builder()
                                .id("d7256d14-b464-48af-94a2-50fc99ed057c")
                                .account(AccountEntity.builder()
                                                      .id(UUID.randomUUID().toString())
                                                      .build())
                                .date(DataUtils.FORMATTER.format(new Date()))
                                .amount(52.79)
                                .fee(3.50)
                                .description("Payment in market")
                                .build();
    }

}
