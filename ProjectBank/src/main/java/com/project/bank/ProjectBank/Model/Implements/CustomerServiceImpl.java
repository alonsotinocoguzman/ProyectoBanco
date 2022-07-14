package com.project.bank.ProjectBank.Model.Implements;

import com.project.bank.ProjectBank.Model.Entity.Customer;
import com.project.bank.ProjectBank.Repository.CustomerRepository;
import com.project.bank.ProjectBank.Model.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> findById(ObjectId customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Mono<Customer> saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Customer> updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Void> deleteCustomer(ObjectId customerId) {
        return customerRepository.deleteById(customerId);
    }
}
