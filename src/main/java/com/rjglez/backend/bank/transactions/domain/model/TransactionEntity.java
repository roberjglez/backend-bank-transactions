package com.rjglez.backend.bank.transactions.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import com.rjglez.backend.bank.transactions.application.utils.DateUtils;
import lombok.*;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransactionEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private AccountEntity account;

    private String date;

    private double amount;

    private double fee;

    private String description;

    public static TransactionEntity of(NewTransactionCommand newTransactionCommand) {

        AccountEntity accountEntity = AccountEntity.builder()
                                                   .id(newTransactionCommand.getAccountIban())
                                                   .build();

        return TransactionEntity.builder()
                                .id(newTransactionCommand.getReference())
                                .account(accountEntity)
                                .date(newTransactionCommand.getDate())
                                .amount(newTransactionCommand.getAmount())
                                .fee(newTransactionCommand.getFee())
                                .description(newTransactionCommand.getDescription())
                                .build();
    }

    public void checkParameters() {

        this.id = Objects.isNull(this.id) ? UUID.randomUUID().toString() : this.id;
        this.date = Objects.isNull(this.date) ? DateUtils.FORMATTER.format(new Date()) : this.date;
    }

    public double getAmountToProcess() {
        return this.amount - this.fee;
    }
}
