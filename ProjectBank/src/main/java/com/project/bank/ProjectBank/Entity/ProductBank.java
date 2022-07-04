package com.project.bank.ProjectBank.Entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ProductBank")
@Getter
@Setter
public class ProductBank {
    @Id
    private ObjectId idProductBank;
    private String typeBankProduct;
}