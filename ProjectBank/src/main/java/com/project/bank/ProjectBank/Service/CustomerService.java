package com.project.bank.ProjectBank.Service;

import com.project.bank.ProjectBank.Entity.Customer;
import reactor.core.publisher.Flux;

public interface CustomerService {
  Flux<Customer> findAll();
}
