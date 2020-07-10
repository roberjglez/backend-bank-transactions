package com.rjglez.backend.bank.transactions.application.response;

import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class TransactionResponseTest {

    @Test
    public void shouldReturnTransactionResponse() {

        // GIVEN
        TransactionEntity transactionEntity = createTransactionEntity();

        // WHEN
        TransactionResponse transactionResponse = TransactionResponse.of(transactionEntity);

        // THEN
        Assertions.assertThat(transactionResponse.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionResponse.getAccountIban()).isEqualTo(transactionEntity.getAccount().getId());
        Assertions.assertThat(transactionResponse.getDate()).isEqualTo(transactionEntity.getDate());
        Assertions.assertThat(transactionResponse.getAmount()).isEqualTo(transactionEntity.getAmount());
        Assertions.assertThat(transactionResponse.getFee()).isEqualTo(transactionEntity.getFee());
        Assertions.assertThat(transactionResponse.getDescription()).isEqualTo(transactionEntity.getDescription());
    }

    private TransactionEntity createTransactionEntity() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return TransactionEntity.builder()
                                .id(UUID.randomUUID().toString())
                                .account(AccountEntity.builder()
                                                      .id(UUID.randomUUID().toString())
                                                      .build())
                                .date(formatter.format(new Date()))
                                .amount(52.79)
                                .fee(3.50)
                                .description("Payment in market")
                                .build();
    }

}
