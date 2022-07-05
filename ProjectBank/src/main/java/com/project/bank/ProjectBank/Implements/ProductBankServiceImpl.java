package com.project.bank.ProjectBank.Implements;

import com.project.bank.ProjectBank.Entity.ProductBank;
import com.project.bank.ProjectBank.Repository.ProductBankRepository;
import com.project.bank.ProjectBank.Service.ProductBankService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductBankServiceImpl implements ProductBankService {
    private final ProductBankRepository bankProductRepository;

    @Override
    public Flux<ProductBank> saveBankProducts(List<ProductBank> bankProducts) {
        return bankProductRepository.saveAll(bankProducts);
    }

    @Override
    public Mono<ProductBank> updateBankProduct(ProductBank bankProduct) {
        return bankProductRepository.save(bankProduct);
    }

    @Override
    public Mono<Void> deleteBankProduct(ObjectId bankProductId) {
        return bankProductRepository.deleteById(bankProductId);
    }

    @Override
    public Flux<ProductBank> getAllBankProducts() {
        return bankProductRepository.findAll();
    }

    @Override
    public Mono<ProductBank> getBankProductById(ObjectId bankProductId) {
        return bankProductRepository.findById(bankProductId);
    }
}