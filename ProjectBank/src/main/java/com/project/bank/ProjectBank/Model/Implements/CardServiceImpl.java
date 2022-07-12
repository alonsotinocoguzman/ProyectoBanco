package com.project.bank.ProjectBank.Model.Implements;

import com.project.bank.ProjectBank.Model.Entity.Card;
import com.project.bank.ProjectBank.Model.Service.CardService;
import com.project.bank.ProjectBank.Repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
  private final CardRepository cardRepository;

  @Override
  public Mono<Card> loadBalance(Double loadBalance, String id) {
    return cardRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new Exception("No se encontró la cuenta ingresada")))
        .filter(availableBalance -> availableBalance.getCreditLimit() < loadBalance)
        .switchIfEmpty(
            Mono.error(new Exception("el monto ingresado debe ser menor al limite maximo")))
        .flatMap(
            saveBalance -> {
              Card card = new Card();
              card.setLoadBalance(loadBalance);
              card.setAvailableBalance(loadBalance);
              return cardRepository.save(card);
            });
  }

  @Override
  public Mono<Card> createAccountInitial(Card car) {
    return cardRepository.save(car);
  }

  @Override
  public Mono<Card> payBalance(Double payBalance, String id) {
    return cardRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new Exception("No se encontró la cuenta")))
        .filter(paybalance -> paybalance.getDebitBalance() < paybalance.getAvailableBalance())
        .switchIfEmpty(Mono.error(new Exception("No tiene deuda")))
        .flatMap(
            savePayBalance -> {
              Card card = new Card();
              card.setAvailableBalance(payBalance);
              card.setLoadBalance(payBalance);
              card.setDebitBalance(0.0);
              return cardRepository.save(card);
            });
  }
}
