package com.rjglez.backend.bank.transactions.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rjglez.backend.bank.transactions.application.command.TransactionCommand;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
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

    public static TransactionEntity of(TransactionCommand transactionCommand) {

        AccountEntity accountEntity = AccountEntity.builder()
                                                   .id(transactionCommand.getAccountIban())
                                                   .build();

        return TransactionEntity.builder()
                                .id(transactionCommand.getReference())
                                .account(accountEntity)
                                .date(transactionCommand.getDate())
                                .amount(transactionCommand.getAmount())
                                .fee(transactionCommand.getFee())
                                .description(transactionCommand.getDescription())
                                .build();
    }

    public void checkParameters() {

        this.id = Objects.isNull(this.id) ? UUID.randomUUID().toString() : this.id;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.date = Objects.isNull(this.date) ? formatter.format(new Date()) : this.date;
    }

    public double getAmountToProcess() {
        return this.amount - this.fee;
    }
}
