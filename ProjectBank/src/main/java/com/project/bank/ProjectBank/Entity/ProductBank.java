package com.project.bank.ProjectBank.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("productbank")
@Getter
@Setter
public class ProductBank {
    @Id
    private Integer idProductBank;
    private String typeBankProduct;

}
