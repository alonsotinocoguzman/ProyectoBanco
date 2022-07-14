package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Model.Entity.BankAccount;
import com.project.bank.ProjectBank.Model.Service.BankAccountService;
import com.project.bank.ProjectBank.Utils.UIUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(UIUtils.BANKACCOUNT_BASEURL)
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PostMapping(UIUtils.BANKACCOUNT_INS)
    public Mono<BankAccount> saveBankAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.saveBankAccount(bankAccount);
    }

    @PutMapping(UIUtils.BANKACCOUNT_UPD)
    public Mono<BankAccount> updateBankAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.updateBankAccount(bankAccount);
    }

    @DeleteMapping(UIUtils.BANKACCOUNT_DEL)
    public Mono<Void> deleteBankAccount(@PathVariable(value = "bankAccountId") ObjectId bankAccountId) {
        return bankAccountService.deleteBankAccount(bankAccountId);
    }

    @GetMapping(UIUtils.BANKACCOUNT_ALL_BY_CUSTOMER)
    public Flux<BankAccount> getAllBankAccountsByCustomer(@PathVariable(value = "customerId") ObjectId customerId) {
        return bankAccountService.getAllBankAccountsByCustomer(customerId);
    }

    @GetMapping(UIUtils.BANKACCOUNT_ID)
    public Mono<BankAccount> getBankAccount(@PathVariable(value = "bankAccountId") ObjectId bankAccountId) {
        return  bankAccountService.getBankAccount(bankAccountId);
    }
}