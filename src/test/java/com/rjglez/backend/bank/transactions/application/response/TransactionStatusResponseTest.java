package com.rjglez.backend.bank.transactions.application.response;

import com.rjglez.backend.bank.transactions.domain.model.AccountEntity;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import com.rjglez.backend.bank.transactions.domain.utils.DataUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class TransactionStatusResponseTest {

    private static final String CLIENT_CHANNEL = "CLIENT";
    private static final String ATM_CHANNEL = "ATM";
    private static final String INTERNAL_CHANNEL = "INTERNAL";

    @Test
    public void shouldReturnTransactionStatusResponseWhenTransactionIsNotStored() {

        // GIVEN
        String reference = "85b37cac-e8c2-46f4-8c77-e11f0cff16b1";

        // WHEN
        TransactionStatusResponse transactionStatusResponse = TransactionStatusResponse.ofTransactionNotStored(reference);

        // THEN
        Assertions.assertThat(transactionStatusResponse.getReference()).isEqualTo(reference);
        Assertions.assertThat(transactionStatusResponse.getStatus()).isEqualTo(TransactionStatusResponse.TransactionStatus.INVALID);
    }

    @ParameterizedTest
    @MethodSource("parametersProvided")
    public void shouldReturnTransactionStatusResponse(TransactionEntity transactionEntity, String channel, TransactionStatusResponse.TransactionStatus expectedStatus) throws ParseException {

        // WHEN
        TransactionStatusResponse transactionStatusResponse = TransactionStatusResponse.of(transactionEntity, channel);

        // THEN
        Assertions.assertThat(transactionStatusResponse.getReference()).isEqualTo(transactionEntity.getId());
        Assertions.assertThat(transactionStatusResponse.getStatus()).isEqualTo(expectedStatus);
        if (Objects.isNull(channel) || channel.isEmpty()) {
            Assertions.assertThat(transactionStatusResponse.getAmount()).isEqualTo(transactionEntity.getAmount());
            Assertions.assertThat(transactionStatusResponse.getFee()).isEqualTo(transactionEntity.getFee());
        } else {
            switch (channel) {
                case CLIENT_CHANNEL:
                case ATM_CHANNEL:
                    Assertions.assertThat(transactionStatusResponse.getAmount()).isEqualTo(transactionEntity.getAmount() - transactionEntity.getFee());
                    break;
                case INTERNAL_CHANNEL:
                    Assertions.assertThat(transactionStatusResponse.getAmount()).isEqualTo(transactionEntity.getAmount());
                    Assertions.assertThat(transactionStatusResponse.getFee()).isEqualTo(transactionEntity.getFee());
                    break;
            }
        }
    }

    private static Stream<Arguments> parametersProvided() {
        return Stream.of(
                Arguments.of(createTransactionEntity("2019-07-16T16:55:42.000Z"), TransactionStatusResponse.CLIENT_CHANNEL, TransactionStatusResponse.TransactionStatus.SETTLED),
                Arguments.of(createTransactionEntity("2019-07-16T16:55:42.000Z"), TransactionStatusResponse.ATM_CHANNEL, TransactionStatusResponse.TransactionStatus.SETTLED),
                Arguments.of(createTransactionEntity("2019-07-16T16:55:42.000Z"), TransactionStatusResponse.INTERNAL_CHANNEL, TransactionStatusResponse.TransactionStatus.SETTLED),
                Arguments.of(createTransactionEntity("2019-07-16T16:55:42.000Z"), null, TransactionStatusResponse.TransactionStatus.SETTLED),
                Arguments.of(createTransactionEntity("2023-07-16T16:55:42.000Z"), TransactionStatusResponse.CLIENT_CHANNEL, TransactionStatusResponse.TransactionStatus.FUTURE),
                Arguments.of(createTransactionEntity("2023-07-16T16:55:42.000Z"), TransactionStatusResponse.ATM_CHANNEL, TransactionStatusResponse.TransactionStatus.PENDING),
                Arguments.of(createTransactionEntity("2023-07-16T16:55:42.000Z"), TransactionStatusResponse.INTERNAL_CHANNEL, TransactionStatusResponse.TransactionStatus.FUTURE),
                Arguments.of(createTransactionEntity("2023-07-16T16:55:42.000Z"), null, TransactionStatusResponse.TransactionStatus.FUTURE),
                Arguments.of(createTransactionEntity(DataUtils.FORMATTER.format(new Date())), TransactionStatusResponse.CLIENT_CHANNEL, TransactionStatusResponse.TransactionStatus.PENDING),
                Arguments.of(createTransactionEntity(DataUtils.FORMATTER.format(new Date())), TransactionStatusResponse.ATM_CHANNEL, TransactionStatusResponse.TransactionStatus.PENDING),
                Arguments.of(createTransactionEntity(DataUtils.FORMATTER.format(new Date())), TransactionStatusResponse.INTERNAL_CHANNEL, TransactionStatusResponse.TransactionStatus.PENDING),
                Arguments.of(createTransactionEntity(DataUtils.FORMATTER.format(new Date())), null, TransactionStatusResponse.TransactionStatus.PENDING)
        );
    }

    private static TransactionEntity createTransactionEntity(String date) {

        return TransactionEntity.builder()
                                .id(UUID.randomUUID().toString())
                                .account(AccountEntity.builder()
                                                      .id(UUID.randomUUID().toString())
                                                      .build())
                                .date(date)
                                .amount(52.79)
                                .fee(3.50)
                                .description("Payment in market")
                                .build();
    }

}
