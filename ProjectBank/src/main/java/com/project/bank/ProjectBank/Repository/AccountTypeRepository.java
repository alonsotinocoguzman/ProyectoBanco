package com.project.bank.ProjectBank.Repository;

import com.project.bank.ProjectBank.Entity.AccountType;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends ReactiveCrudRepository<AccountType, ObjectId> {}
