package com.project.bank.ProjectBank.Model.Service;

import com.project.bank.ProjectBank.Model.Entity.Card;
import org.bson.types.ObjectId;
import reactor.core.publisher.Mono;

public interface CardService {
Mono<Card> loadBalance(Double loadBalance, String id);
Mono<Card> createAccountInitial(Card car);
Mono<Card> payBalance(Double payBalance, String id);
}
