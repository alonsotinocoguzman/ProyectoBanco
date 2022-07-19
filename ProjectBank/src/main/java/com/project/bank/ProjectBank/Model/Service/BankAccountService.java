package com.project.bank.ProjectBank.Model.Service;

import com.project.bank.ProjectBank.Model.Entity.BankAccount;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {
    Mono<BankAccount> saveBankAccount(BankAccount bankAccount);
    Mono<BankAccount> updateBankAccount(BankAccount bankAccount);
    Mono<Void> deleteBankAccount(ObjectId bankAccountId);
    Flux<BankAccount> getAllBankAccountsByCustomer(String documentNumber);
    Mono<BankAccount> getBankAccount(ObjectId bankAccountId);
}