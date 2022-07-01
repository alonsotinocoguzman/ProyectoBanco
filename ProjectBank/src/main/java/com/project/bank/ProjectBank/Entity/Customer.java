package com.project.bank.ProjectBank.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customer")
@Getter
@Setter
public class Customer {
    @Id
    private Integer customerId;
    private String name;
    private String lastName;
    private String email;
    private String dni;
    private String phone;
}
