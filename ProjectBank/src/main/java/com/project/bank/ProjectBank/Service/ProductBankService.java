package com.project.bank.ProjectBank.Service;

import com.project.bank.ProjectBank.Entity.ProductBank;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductBankService {
    Flux<ProductBank> saveBankProducts(List<ProductBank> bankProducts);
    Mono<ProductBank> updateBankProduct(ProductBank bankProduct);
    Mono<Void> deleteBankProduct(ObjectId bankProductId);
    Flux<ProductBank> getAllBankProducts();
    Mono<ProductBank> getBankProductById(ObjectId bankProductId);
}
