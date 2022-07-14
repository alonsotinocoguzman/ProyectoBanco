package com.project.bank.ProjectBank.Model.Implements;

import com.project.bank.ProjectBank.Model.Entity.BankAccount;
import com.project.bank.ProjectBank.Model.Entity.Customer;
import com.project.bank.ProjectBank.Repository.BankAccountRepository;
import com.project.bank.ProjectBank.Model.Service.BankAccountService;
import com.project.bank.ProjectBank.Model.Service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerService customerService;

    @Override
    public Mono<BankAccount> saveBankAccount(BankAccount bankAccount) {
        log.info("INICIO saveBankAccountImpl");

        Boolean isValid = validateAccountNumber(bankAccount);

        log.info("FIN saveBankAccountImpl");

        if (isValid == true) {
            return bankAccountRepository.save(bankAccount);
        } else {
            return Mono.empty();
        }
    }

    private Boolean validateAccountNumber(BankAccount bankAccount) {
        log.info("INICIO validateAccountNumber()");

        Boolean isValid = false;
        log.info("isValid: " + isValid.toString());

        Flux<BankAccount> bankAccounts = getAllBankAccountsByCustomer(bankAccount.getCustomerId());
        log.info("N° bankAccounts: " + bankAccounts.count().toString());

        Flux<BankAccount> savingsAccounts = bankAccounts.filter(account -> account.getAccountTypeId().equals(1)); //AHORRO
        Flux<BankAccount> checkingAccounts = bankAccounts.filter(account -> account.getAccountTypeId().equals(2)); //CORRIENTE
        Flux<BankAccount> fixedTermAccounts = bankAccounts.filter((account -> account.getAccountTypeId().equals(3))); //PLAZO FIJO

        Long savingsAccountLong = Long.parseLong(savingsAccounts.count().toString());
        Long checkingAccountLong = Long.parseLong(checkingAccounts.count().toString());
        Long fixedTermAccountLong = Long.parseLong(fixedTermAccounts.count().toString());

        Integer savingsAccountQuantity = Math.toIntExact(savingsAccountLong);
        Integer checkingAccountQuantity = Math.toIntExact(checkingAccountLong);
        Integer fixedTermAccountQuantity = Math.toIntExact(fixedTermAccountLong);

        log.info("N° Cuentas de Ahorros: " + savingsAccountQuantity);
        log.info("N° Cuentas Corrientes: " + checkingAccountQuantity);
        log.info("N° Cuentas a Plazo Fijo: " + fixedTermAccountQuantity);

        Customer customer = customerService.findById(bankAccount.getCustomerId()).block();
        log.info("customerData: " + customer.toString());

        if (customer.getCustomerTypeId().equals("PER")) {
            log.info("Cuenta Personal");
            if (savingsAccountQuantity <= 1 && checkingAccountQuantity <= 1 && fixedTermAccountQuantity <= 1) {
                isValid = true;
                log.info("isValid:" + isValid);
            }
        } else if (customer.getCustomerTypeId().equals("EMP")) {
            log.info("Cuenta Empresarial");
            if (!bankAccount.getAccountTypeId().equals(1) || !bankAccount.getAccountTypeId().equals(3)) {
                isValid = true;
                log.info("isValid:" + isValid);
            }
        }

        log.info("FIN validateAccountNumber()");
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
        log.info("INICIO getAllBankAccountsByCustomer");
        log.info("customerId: " + customerId.toString());
        log.info("BUSQUEDA FIND ALL: " + bankAccountRepository.findAll().filter(x -> x.getCustomerId().equals(customerId)).subscribe(i -> System.out.println(i)));
        return null;//bankAccountRepository.findAll().subscribe();//.filter(bankAccount -> bankAccount.getCustomerId().equals(customerId)));
    }

    @Override
    public Mono<BankAccount> getBankAccount(ObjectId bankAccountId) {
        return bankAccountRepository.findById(bankAccountId);
    }
}