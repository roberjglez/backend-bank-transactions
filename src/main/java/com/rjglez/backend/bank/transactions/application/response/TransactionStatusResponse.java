package com.rjglez.backend.bank.transactions.application.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rjglez.backend.bank.transactions.application.utils.DateUtils;
import com.rjglez.backend.bank.transactions.domain.model.TransactionEntity;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionStatusResponse {

    public static final String CLIENT_CHANNEL   = "CLIENT";
    public static final String ATM_CHANNEL      = "ATM";
    public static final String INTERNAL_CHANNEL = "INTERNAL";

    private String            reference;
    private TransactionStatus status;
    private Double            amount;
    private Double            fee;

    public static TransactionStatusResponse ofTransactionNotStored(String reference) {

        return TransactionStatusResponse.builder()
                                        .reference(reference)
                                        .status(TransactionStatus.INVALID)
                                        .build();
    }

    public static TransactionStatusResponse of(TransactionEntity transactionEntity, String channel) throws ParseException {

        Date transactionDate = DateUtils.FORMATTER.parse(transactionEntity.getDate());

        TransactionStatusResponse transactionStatusResponse = null;

        if (DateUtils.isToday(transactionDate)) {
            log.info("Transaction date with reference {} is today: {}", transactionEntity.getId(), transactionDate);
            transactionStatusResponse = transactionToday(transactionEntity, channel);
        } else if (DateUtils.isBeforeToday(transactionDate)) {
            log.info("Transaction date with reference {} is before today: {}", transactionEntity.getId(), transactionDate);
            transactionStatusResponse = transactionBeforeToday(transactionEntity, channel);
        } else if (DateUtils.isAfterToday(transactionDate)) {
            log.info("Transaction date with reference {} is after today: {}", transactionEntity.getId(), transactionDate);
            transactionStatusResponse = transactionAfterToday(transactionEntity, channel);
        }

        return transactionStatusResponse;
    }

    private static TransactionStatusResponse transactionAfterToday(TransactionEntity transactionEntity, String channel) {
        TransactionStatusResponse transactionStatusResponse = TransactionStatusResponse.builder()
                                                                                       .reference(transactionEntity.getId())
                                                                                       .build();

        switch (channel) {
            case CLIENT_CHANNEL:
                transactionStatusResponse.setStatus(TransactionStatus.FUTURE);
                transactionStatusResponse.setAmount(transactionEntity.getAmountToProcess());
                break;
            case ATM_CHANNEL:
                transactionStatusResponse.setStatus(TransactionStatus.PENDING);
                transactionStatusResponse.setAmount(transactionEntity.getAmountToProcess());
                break;
            case INTERNAL_CHANNEL:
                transactionStatusResponse.setStatus(TransactionStatus.FUTURE);
                transactionStatusResponse.setAmount(transactionEntity.getAmount());
                transactionStatusResponse.setFee(transactionEntity.getFee());
                break;
        }

        return transactionStatusResponse;
    }

    private static TransactionStatusResponse transactionBeforeToday(TransactionEntity transactionEntity, String channel) {
        TransactionStatusResponse transactionStatusResponse = TransactionStatusResponse.builder()
                                                                                       .reference(transactionEntity.getId())
                                                                                       .build();
        switch (channel) {
            case CLIENT_CHANNEL:
            case ATM_CHANNEL:
                transactionStatusResponse.setStatus(TransactionStatus.SETTLED);
                transactionStatusResponse.setAmount(transactionEntity.getAmountToProcess());
                break;
            case INTERNAL_CHANNEL:
                transactionStatusResponse.setStatus(TransactionStatus.SETTLED);
                transactionStatusResponse.setAmount(transactionEntity.getAmount());
                transactionStatusResponse.setFee(transactionEntity.getFee());
                break;
        }

        return transactionStatusResponse;
    }

    private static TransactionStatusResponse transactionToday(TransactionEntity transactionEntity, String channel) {
        TransactionStatusResponse transactionStatusResponse = TransactionStatusResponse.builder()
                                                                                       .reference(transactionEntity.getId())
                                                                                       .build();
        switch (channel) {
            case CLIENT_CHANNEL:
            case ATM_CHANNEL:
                transactionStatusResponse.setStatus(TransactionStatus.PENDING);
                transactionStatusResponse.setAmount(transactionEntity.getAmountToProcess());
                break;
            case INTERNAL_CHANNEL:
                transactionStatusResponse.setStatus(TransactionStatus.PENDING);
                transactionStatusResponse.setAmount(transactionEntity.getAmount());
                transactionStatusResponse.setFee(transactionEntity.getFee());
                break;
        }

        return transactionStatusResponse;
    }

    public enum TransactionStatus {
        INVALID,
        PENDING,
        SETTLED,
        FUTURE
    }
}
