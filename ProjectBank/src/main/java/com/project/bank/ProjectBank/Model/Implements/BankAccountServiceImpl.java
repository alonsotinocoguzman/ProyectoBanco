package com.project.bank.ProjectBank.Model.Implements;

import com.project.bank.ProjectBank.Model.Entity.BankAccount;
import com.project.bank.ProjectBank.Model.Entity.Customer;
import com.project.bank.ProjectBank.Repository.BankAccountRepository;
import com.project.bank.ProjectBank.Model.Service.BankAccountService;
import com.project.bank.ProjectBank.Model.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerService customerService;

    @Override
    public Mono<BankAccount> saveBankAccount(BankAccount bankAccount) {
        Boolean isValid = validateAccountNumber(bankAccount);
        if (isValid == true) {
            return bankAccountRepository.save(bankAccount);
        } else {
            return Mono.empty();
        }
    }

    private Boolean validateAccountNumber(BankAccount bankAccount) {
        Boolean isValid = false;

        Flux<BankAccount> bankAccounts = getAllBankAccountsByCustomer(bankAccount.getCustomerId());
        Flux<BankAccount> savingsAccounts = bankAccounts.filter(account -> account.getAccountTypeId().equals(1)); //AHORRO
        Flux<BankAccount> checkingAccounts = bankAccounts.filter(account -> account.getAccountTypeId().equals(2)); //CORRIENTE
        Flux<BankAccount> fixedTermAccounts = bankAccounts.filter((account -> account.getAccountTypeId().equals(3))); //PLAZO FIJO

        Long savingsAccountLong = Long.parseLong(savingsAccounts.count().toString());
        Long checkingAccountLong = Long.parseLong(checkingAccounts.count().toString());
        Long fixedTermAccountLong = Long.parseLong(fixedTermAccounts.count().toString());

        Integer savingsAccountQuantity = Math.toIntExact(savingsAccountLong);
        Integer checkingAccountQuantity = Math.toIntExact(checkingAccountLong);
        Integer fixedTermAccountQuantity = Math.toIntExact(fixedTermAccountLong);

        Customer customer = customerService.findById(bankAccount.getCustomerId()).block();

        if (customer.getCustomerTypeId().equals("PER")) {
            if (savingsAccountQuantity <= 1 && checkingAccountQuantity <= 1 && fixedTermAccountQuantity <= 1) {
                isValid = true;
            }
        } else if (customer.getCustomerTypeId().equals("EMP")) {
            if (!bankAccount.getAccountTypeId().equals(1) || !bankAccount.getAccountTypeId().equals(3)) {
                isValid = true;
            }
        }
        return isValid;
    }

    @Override
    public Mono<BankAccount> updateBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public Mono<Void> deleteBankAccount(ObjectId bankAccountId) {
        return bankAccountRepository.deleteById(bankAccountId);
    }

    @Override
    public Flux<BankAccount> getAllBankAccountsByCustomer(ObjectId customerId) {
        return bankAccountRepository.findAll().filter(bankAccount -> bankAccount.getCustomerId().equals(customerId));
    }

    @Override
    public Mono<BankAccount> getBankAccount(ObjectId bankAccountId) {
        return bankAccountRepository.findById(bankAccountId);
    }
}