package com.project.bank.ProjectBank.Repository;

import com.project.bank.ProjectBank.Entity.ProductBank;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBankRepository extends ReactiveCrudRepository<ProductBank, ObjectId> {
}
