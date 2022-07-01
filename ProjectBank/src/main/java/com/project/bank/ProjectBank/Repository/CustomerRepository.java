package com.project.bank.ProjectBank.Repository;

import com.project.bank.ProjectBank.Entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<Customer,Integer> {
}
