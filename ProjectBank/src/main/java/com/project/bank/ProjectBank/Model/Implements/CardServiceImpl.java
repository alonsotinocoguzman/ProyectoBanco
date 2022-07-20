package com.project.bank.ProjectBank.Model.Implements;

import com.project.bank.ProjectBank.Model.Entity.Card;
import com.project.bank.ProjectBank.Model.Entity.Dto.CardDto;
import com.project.bank.ProjectBank.Model.Service.CardService;
import com.project.bank.ProjectBank.Repository.CardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
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
  public Flux<Card> createAccountInitial(Card car) {
    return cardRepository
        .findAll()
        .switchIfEmpty(cardRepository.save(car))
        .flatMap(
            customer -> {
              if (customer.getNumberDocument().equals(car.getNumberDocument())) {
                log.info("valido documento");
                if (customer.isOverdueDebt()) {
                  log.info("valido isOverdueDebt");
                  Flux.error(new Error("cuenta con deuda"));
                } else {
                  log.info("no tiene deuda");
                  return cardRepository.save(car);
                }
              } else {
                log.info("cliente no encontrado");
                return cardRepository.save(car);
              }
              return Flux.empty();
            });
  }

  private Flux<Card> overdueDebt(String nroDocument) {
    return cardRepository
        .findAll()
        .filter(z -> z.getNumberDocument().equals(nroDocument))
        .filter(Card::isOverdueDebt);
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

  @Override
  public Mono<CardDto> getBalance(String id) {
    return cardRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new Exception("No se encontraron movimientos")))
        .map(this::convertEntityToDto);
  }

  @Override
  public Flux<CardDto> getBalanceByCustomerId(String id) {
    return cardRepository
        .findAll()
        .filter(customerId -> customerId.getCustomerId().equals(id))
        .switchIfEmpty(Mono.error(new Exception("No se encontraron movimientos para este cliente")))
        .map(this::convertEntityToDto);
  }

  private CardDto convertEntityToDto(Card card) {
    CardDto cardDto = new CardDto();
    cardDto.setAvailableBalance(card.getAvailableBalance());
    return cardDto;
  }
}
