package com.rjglez.backend.bank.transactions.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rjglez.backend.bank.transactions.application.command.NewTransactionCommand;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
                                .fee(Objects.isNull(newTransactionCommand.getFee()) ? 0.0 : newTransactionCommand.getFee())
                                .description(newTransactionCommand.getDescription())
                                .build();
    }

    public double getAmountToProcess() {
        return this.amount - this.fee;
    }
}
