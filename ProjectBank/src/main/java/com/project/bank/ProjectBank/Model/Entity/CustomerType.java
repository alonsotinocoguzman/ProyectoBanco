package com.project.bank.ProjectBank.Model.Entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CustomerType")
@Getter
@Setter
public class CustomerType {
    @Id
    private ObjectId id;
    private String customerTypeId;
    private String customerTypeDescription;
}