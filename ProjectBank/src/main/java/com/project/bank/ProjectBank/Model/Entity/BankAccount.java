package com.project.bank.ProjectBank.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}