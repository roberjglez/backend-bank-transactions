package com.rjglez.backend.bank.transactions.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rjglez.backend.bank.transactions.application.command.NewAccountCommand;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountEntity {

    @Id
    private String id;

    private double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TransactionEntity> transactions;

    public static AccountEntity of(NewAccountCommand newAccountCommand) {

        return AccountEntity.builder()
                            .id(newAccountCommand.getIban())
                            .balance(newAccountCommand.getBalance())
                            .build();

    }

    public void addTransaction(TransactionEntity transaction) {
        this.transactions.add(transaction);
    }

    public boolean isTransactionAllowed(double amountToProcess) {
        double finalAmount = this.balance + amountToProcess;
        return finalAmount >= 0;
    }

    public void modifyBalance(double amountToProcess) {
        double newBalance = this.balance + amountToProcess;
        this.setBalance(newBalance);
    }

}
