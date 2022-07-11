package com.project.bank.ProjectBank.Implements;

import com.project.bank.ProjectBank.Model.Entity.CustomerType;
import com.project.bank.ProjectBank.Repository.CustomerTypeRepository;
import com.project.bank.ProjectBank.Service.CustomerTypeService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerTypeServiceImpl implements CustomerTypeService {
    private final CustomerTypeRepository customerTypeRepository;

    @Override
    public Flux<CustomerType> saveCustomerTypes(List<CustomerType> customerTypes) {
        return customerTypeRepository.saveAll(customerTypes);
    }

    @Override
    public Mono<CustomerType> updateCustomerType(CustomerType customerType) {
        return customerTypeRepository.save(customerType);
    }

    @Override
    public Mono<Void> deleteCustomerType(ObjectId customerTypeId) {
        return customerTypeRepository.deleteById(customerTypeId);
    }

    @Override
    public Flux<CustomerType> getAllCustomerTypes() {
        return customerTypeRepository.findAll();
    }

    @Override
    public Mono<CustomerType> getCustomerTypeById(ObjectId customerTypeId) {
        return customerTypeRepository.findById(customerTypeId);
    }
}