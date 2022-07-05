package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Entity.ProductBank;
import com.project.bank.ProjectBank.Service.ProductBankService;
import com.project.bank.ProjectBank.Utils.UIUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(UIUtils.BANKPRODUCT_BASEURL)
@Slf4j
@AllArgsConstructor
public class ProductBankController {
    private final ProductBankService bankProductService;

    @PostMapping(UIUtils.BANKPRODUCT_INS)
    public Flux<ProductBank> saveBankProducts(@RequestBody List<ProductBank> bankProducts) {
        log.info("Ingreso a saveBankProducts");
        return bankProductService.saveBankProducts(bankProducts);
    }

    @PutMapping(UIUtils.BANKPRODUCT_UPD)
    public Mono<ProductBank> updateBankProduct(@RequestBody ProductBank productBank) {
        log.info("Ingreso a updateBankProduct");
        return bankProductService.updateBankProduct(productBank);
    }

    @DeleteMapping(UIUtils.BANKPRODUCT_DEL)
    public Mono<Void> deleteBankProduct(@PathVariable(value = "bankProductId") ObjectId bankProductId) {
        log.info("Ingreso a deleteBankProduct");
        return bankProductService.deleteBankProduct(bankProductId);
    }

    @GetMapping(UIUtils.BANKPRODUCT_ALL)
    public Flux<ProductBank> getAllBankProducts() {
        log.info("Ingreso a getAllBankProducts");
        return bankProductService.getAllBankProducts();
    }

    @GetMapping(UIUtils.BANKPRODUCT_ID)
    public Mono<ProductBank> getBankProductById(@PathVariable(value = "bankProductId") ObjectId bankProductId) {
        log.info("Ingreso a getBankProductById");
        return bankProductService.getBankProductById(bankProductId);
    }
}