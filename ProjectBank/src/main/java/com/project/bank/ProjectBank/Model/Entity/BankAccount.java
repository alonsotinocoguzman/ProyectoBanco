package com.project.bank.ProjectBank.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BankAccount")
public class BankAccount {
    @Id
    private ObjectId bankAccountId;
    private String documentNumber;
    private String numberAccount;
    private Float accountBalance;
    private Integer accountTypeId;
    private Double startingBalance = 0.0;
    private Integer maxDepositTransaction = 15;
    private String creationDepositDate = DateFormat.getInstance().format(new Date());
    private Integer maximumTransactionWithdrawal = 15;
    private String creationTransactionWithdrawalDate = DateFormat.getInstance().format(new Date());
    private String numberAccountToOther;
    private Double commission = 1.5;
    private String profile;
    private String creationDate = DateFormat.getInstance().format(new Date());
}