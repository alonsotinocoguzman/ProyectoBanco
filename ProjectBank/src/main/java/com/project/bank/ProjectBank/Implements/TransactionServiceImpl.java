package com.project.bank.ProjectBank.Implements;

import com.project.bank.ProjectBank.Model.Entity.Transaction;
import com.project.bank.ProjectBank.Repository.TransactionRepository;
import com.project.bank.ProjectBank.Service.TransactionService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    @Override
    public Flux<Transaction> saveTransations(List<Transaction> transactionList) {
        return transactionRepository.saveAll(transactionList);
    }

    @Override
    public Mono<Transaction> updateTransation(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Void> deleteTransationById(ObjectId id) {
        return transactionRepository.deleteById(id);
    }

    @Override
    public Flux<Transaction> getAllTransations() {
        return transactionRepository.findAll();
    }

    @Override
    public Mono<Transaction> getTransationById(ObjectId id) {
        return transactionRepository.findById(id);
    }
}
