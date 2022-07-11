package com.project.bank.ProjectBank.Service;

import com.project.bank.ProjectBank.Model.Entity.ProductBank;
import com.project.bank.ProjectBank.Model.Entity.Transaction;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TransactionService {
    Flux<Transaction> saveTransations(List<Transaction> transactionList);
    Mono<Transaction> updateTransation(Transaction transaction);
    Mono<Void> deleteTransationById(ObjectId id);
    Flux<Transaction> getAllTransations();
    Mono<Transaction> getTransationById(ObjectId id);
}
