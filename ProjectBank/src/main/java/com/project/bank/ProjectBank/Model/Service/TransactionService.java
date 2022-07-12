package com.project.bank.ProjectBank.Model.Service;

import com.project.bank.ProjectBank.Model.Entity.Transaction;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<Transaction> saveTransations(Transaction transaction);
    Mono<Transaction> updateTransation(Transaction transaction);
    Mono<Void> deleteTransationById(ObjectId id);
    Flux<Transaction> getAllTransations();
    Mono<Transaction> getTransationById(ObjectId id);
    Mono<Double>getTransactionBalance(ObjectId id);
}
