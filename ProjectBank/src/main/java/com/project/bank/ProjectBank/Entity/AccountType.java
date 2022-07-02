package com.project.bank.ProjectBank.Entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AccountType")
@Getter
@Setter
public class AccountType {
    @Id
    private ObjectId idAccountType;
    private String description;

}
