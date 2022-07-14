package com.project.bank.ProjectBank.Repository;

import com.project.bank.ProjectBank.Model.Entity.BankAccount;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends ReactiveCrudRepository<BankAccount, ObjectId> {
}