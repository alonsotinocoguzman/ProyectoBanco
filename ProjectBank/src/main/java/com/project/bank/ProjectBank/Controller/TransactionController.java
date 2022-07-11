package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Model.Entity.Transaction;
import com.project.bank.ProjectBank.Service.TransactionService;
import com.project.bank.ProjectBank.Utils.UIUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(UIUtils.BASEURL_TRANSACTION)
@Slf4j
@AllArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping(UIUtils.TRANSACTION_INS)
  public Flux<Transaction> saveTransaction(@RequestBody List<Transaction> transactionList) {
    return transactionService.saveTransations(transactionList);
  }

  @PutMapping(UIUtils.TRANSACTION_UPD)
  public Mono<Transaction> updateTransaction(@RequestBody Transaction transaction) {
    return transactionService.updateTransation(transaction);
  }

  @DeleteMapping(UIUtils.TRANSACTION_DEL)
  public Mono<Void> deleteTransaction(@PathVariable(value = "id") ObjectId id) {
    return transactionService.deleteTransationById(id);
  }

  @GetMapping(UIUtils.TRANSACTION_ALL)
  public Flux<Transaction> getAllTransaction() {
    return transactionService.getAllTransations();
  }

  @GetMapping(UIUtils.TRANSACTION_ID)
  public Mono<Transaction> getTransactionById(@PathVariable(value = "id") ObjectId id) {
    return transactionService.getTransationById(id);
  }
}
