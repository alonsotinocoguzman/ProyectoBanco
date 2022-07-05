package com.project.bank.ProjectBank.Repository;

import com.project.bank.ProjectBank.Entity.CustomerType;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepository extends ReactiveCrudRepository<CustomerType, ObjectId> {
}