package com.project.bank.ProjectBank.Repository;

import com.project.bank.ProjectBank.Model.Entity.TransactionType;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends ReactiveCrudRepository<TransactionType, ObjectId> {
}
