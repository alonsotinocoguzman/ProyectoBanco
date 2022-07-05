package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Entity.AccountType;
import com.project.bank.ProjectBank.Service.AccountTypeService;
import com.project.bank.ProjectBank.Utils.UIUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UIUtils.BASEURLACCOUNTTYPE)
@Slf4j
@AllArgsConstructor
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @GetMapping(UIUtils.ACCOUNT_TYPE_ALL)
    public Flux<AccountType> getAllAccountType() {
        log.info("Ingreso a getAllAccountType");
        return accountTypeService.findAll();
    }

    @GetMapping(UIUtils.ACCOUNT_TYPE_ID)
    public Mono<AccountType> getAccountTypeById(@PathVariable(value = "idAccountType") Integer idAccountType) {
        log.info("Ingreso a getAccountTypeById");
        return accountTypeService.findById(idAccountType);
    }

    @PostMapping(UIUtils.ACCOUNT_TYPE_INS)
    public Flux<AccountType> saveAccountType(@RequestBody Flux<AccountType> AccountType){
        log.info("Ingreso a saveAccountType");
        return accountTypeService.saveAccountType(AccountType);
    }

    @PutMapping(UIUtils.ACCOUNT_TYPE_UPD)
    public Mono<AccountType> updateAccountType(@RequestBody AccountType AccountType){
        log.info("Ingreso a updateAccountType");
        return accountTypeService.updateAccountType(AccountType);
    }

    @DeleteMapping(UIUtils.ACCOUNT_TYPE_DEL)
    public Mono<Void> deleteAccountType(@PathVariable(value = "id") ObjectId idAccountType){
        log.info("Ingreso a deleteAccountType");
        return accountTypeService.deleteAccountType(idAccountType);
    }

}
