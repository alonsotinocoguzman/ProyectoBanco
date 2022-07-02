package com.project.bank.ProjectBank.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Customer")
@Data
public class Customer {
    @Id
    @BsonIgnore
    private ObjectId customerId;
    private String name;
    private String lastName;
    private String email;
    private String dni;
    private String phone;
}
