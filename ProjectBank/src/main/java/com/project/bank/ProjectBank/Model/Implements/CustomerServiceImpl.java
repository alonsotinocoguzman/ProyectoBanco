package com.project.bank.ProjectBank.Model.Implements;

import com.project.bank.ProjectBank.Model.Entity.BankAccount;
import com.project.bank.ProjectBank.Model.Entity.Card;
import com.project.bank.ProjectBank.Model.Entity.Customer;
import com.project.bank.ProjectBank.Model.Service.BankAccountService;
import com.project.bank.ProjectBank.Model.Service.CardService;
import com.project.bank.ProjectBank.Repository.BankAccountRepository;
import com.project.bank.ProjectBank.Repository.CardRepository;
import com.project.bank.ProjectBank.Repository.CustomerRepository;
import com.project.bank.ProjectBank.Model.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> findByDocumentNumber(String documentNumber) {
        return customerRepository.findAll()
                .filter(x->x.getDni().equals(documentNumber))
                .elementAt(0);
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

    @Override
    public Flux<Object> getCustomerAllProucts(String nroDocument) {
        Flux<Card> cardFlux= cardRepository.findAll().filter(z->z.getNumberDocument().equals(nroDocument)).switchIfEmpty(Flux.error(new IOException("no hay cards")));
        Flux<BankAccount> bankAccountFlux=bankAccountRepository.findAll().filter(z->z.getDocumentNumber().equals(nroDocument)).switchIfEmpty(Flux.error(new IOException("no hay cuentas")));
        return Flux.merge(cardFlux,bankAccountFlux);
    }
}
