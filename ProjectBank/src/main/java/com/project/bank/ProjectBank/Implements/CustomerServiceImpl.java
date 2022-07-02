package com.project.bank.ProjectBank.Implements;

import com.project.bank.ProjectBank.Entity.Customer;
import com.project.bank.ProjectBank.Repository.CustomerRepository;
import com.project.bank.ProjectBank.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }
}
