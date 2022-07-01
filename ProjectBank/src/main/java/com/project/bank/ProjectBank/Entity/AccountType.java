package com.project.bank.ProjectBank.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("accounttype")
@Getter
@Setter
public class AccountType {
    @Id
    private Integer idAccountType;
    private String description;

}
