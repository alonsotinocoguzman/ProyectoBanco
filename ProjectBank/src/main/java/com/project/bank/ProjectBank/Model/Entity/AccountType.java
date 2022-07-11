package com.project.bank.ProjectBank.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "AccountType")
@AllArgsConstructor
@NoArgsConstructor
public class AccountType implements Serializable {
  @Id private String id;
  private Integer idAccountType;
  private String description;
  private List<String> productBankList;

}
