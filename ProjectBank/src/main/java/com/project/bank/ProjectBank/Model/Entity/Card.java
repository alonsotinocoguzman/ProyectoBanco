package com.project.bank.ProjectBank.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "card")
public class Card {
  @Id private ObjectId id;
  private String numberAccount;
  private Double availableBalance;
  private Double loadBalance;
  private Double debitBalance;
  private Double creditLimit;
  private String customerId;
}
