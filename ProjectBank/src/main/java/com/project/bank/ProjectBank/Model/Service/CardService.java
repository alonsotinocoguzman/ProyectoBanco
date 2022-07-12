package com.project.bank.ProjectBank.Model.Service;

import com.project.bank.ProjectBank.Model.Entity.Card;
import com.project.bank.ProjectBank.Model.Entity.Dto.CardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardService {
  Mono<Card> loadBalance(Double loadBalance, String id);

  Mono<Card> createAccountInitial(Card car);

  Mono<Card> payBalance(Double payBalance, String id);

  Mono<CardDto> getBalance(String id);
  Flux<CardDto> getBalanceByCustomerId(String id);
}
