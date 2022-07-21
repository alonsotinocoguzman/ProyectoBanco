package com.project.bank.ProjectBank.Repository;

import com.project.bank.ProjectBank.Model.Entity.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, ObjectId> {}
