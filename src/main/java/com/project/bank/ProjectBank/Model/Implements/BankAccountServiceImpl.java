package com.project.bank.ProjectBank.Model.Implements;

import com.project.bank.ProjectBank.Enum.Profile;
import com.project.bank.ProjectBank.Model.Entity.BankAccount;
import com.project.bank.ProjectBank.Model.Entity.Customer;
import com.project.bank.ProjectBank.Model.Service.BankAccountService;
import com.project.bank.ProjectBank.Model.Service.CustomerService;
import com.project.bank.ProjectBank.Repository.BankAccountRepository;
import com.project.bank.ProjectBank.Repository.CardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class BankAccountServiceImpl implements BankAccountService {
  private final BankAccountRepository bankAccountRepository;
  private final CustomerService customerService;
  private final CardRepository cardRepository;

  @Override
  public Mono<BankAccount> saveBankAccount(BankAccount bankAccount) throws Exception {
    log.info("01 INICIO saveBankAccountImpl");
    Mono<BankAccount> bankAccountSaved = Mono.empty();
    // Mono<Boolean> isValid = validateSave(bankAccount);
    /*Mono<Boolean> isValid = Mono.just(true);
    bankAccountSaved = bankAccountRepository.save(bankAccount);*/
    /*isValid = validateSave(bankAccount);
    isValid.subscribe(aBoolean -> {
        log.info("02 valor boobleano traido " + aBoolean.booleanValue());
        log.info("03 FIN saveBankAccountImpl");
    });*/
    // Mono<BankAccount> bankAccountSaved = bankAccountRepository.save(bankAccount);
    /*if (isValid == Mono.just(true)) {
        bankAccountSaved = bankAccountRepository.save(bankAccount);
        log.info("Se guardó la cuenta bancaria");
    }*/

    return save(bankAccount);
  }

  private Mono<BankAccount> save(BankAccount bankAccount) throws Exception {
    if (bankAccount.getProfile().equals(Profile.VIP.toString())
        && bankAccount.getStartingBalance() == 0.0) {
      cardRepository
          .findAll()
          .filter(
              customerId -> customerId.getNumberDocument().equals(bankAccount.getDocumentNumber()))
          .switchIfEmpty(Mono.error(new IOException("no existe customerId")))
          .filter(
              numberAccountToOther ->
                  numberAccountToOther
                      .getNumberAccountToOther()
                      .equals(bankAccount.getNumberAccountToOther()))
          .switchIfEmpty(Mono.error(new IOException("no existe cuenta")))
          .map(
              saveData -> {
                bankAccount.setCommission(0.0);
                return bankAccountRepository.save(bankAccount);
              });
    } else if (bankAccount.getProfile().equals(Profile.PYME.toString())) {
      cardRepository
          .findAll()
          .filter(
              customerId -> customerId.getNumberDocument().equals(bankAccount.getDocumentNumber()))
          .map(
              saveData -> {
                bankAccount.setCommission(0.0);
                return bankAccountRepository.save(bankAccount);
              });
        bankAccount.setCommission(0.0);
    }
      return bankAccountRepository.save(bankAccount);
  }

  private Mono<Boolean> validateSave(BankAccount bankAccount) {
    log.info("INICIO validateSave()");
    log.info("bankAccount Document Number: " + bankAccount.getDocumentNumber());

    AtomicReference<Boolean> isValid = new AtomicReference<>(false);
    log.info("isValid: " + isValid);

    Mono<Customer> customerMono =
        customerService.findByDocumentNumber(bankAccount.getDocumentNumber());
    customerMono.subscribe(
        x -> {
          // System.out.println("clienteData: " + x.getName() + " " + x.getLastName() + " " +
          // x.getDni());
          log.info("clienteData: " + x.getName() + " " + x.getLastName() + " " + x.getDni());

          Flux<BankAccount> bankAccountList = getAllBankAccountsByCustomer(x.getDni());

          /*bankAccountList.count().subscribe(i -> {
              System.out.println("ENTRANDO AL SUBSCRIBE DE COUNT");
              System.out.println("Cantidad de Cuentas Bancarias XD: " + i.intValue());
              System.out.println("SALIENDO DEL SUBSCRIBE DE COUNT");
          });*/

          List<BankAccount> bankAccounts = bankAccountList.toStream().collect(Collectors.toList());

          Integer intBankAccounts = Math.toIntExact(bankAccounts.stream().count());
          // System.out.println("N° bankAccounts" + intBankAccounts);
          log.info("N° bankAccounts -> " + intBankAccounts);

          List<BankAccount> ahorros =
              bankAccounts.stream()
                  .filter(y -> y.getAccountTypeId().equals(1))
                  .collect(Collectors.toList());
          // System.out.println("N° Ahorros -> " + ahorros.size());
          log.info("N° Ahorros -> " + ahorros.size());

          List<BankAccount> corrientes =
              bankAccounts.stream()
                  .filter(y -> y.getAccountTypeId().equals(2))
                  .collect(Collectors.toList());
          // System.out.println("N° Corrientes -> " + corrientes.size());
          log.info("N° Corrientes -> " + corrientes.size());

          List<BankAccount> plazosFijo =
              bankAccounts.stream()
                  .filter(y -> y.getAccountTypeId().equals(3))
                  .collect(Collectors.toList());
          // System.out.println("N° Plazos Fijo -> " + plazosFijo.size());
          log.info("N° Plazos Fijo -> " + plazosFijo.size());

          // System.out.println("TIPO CLIENTE: " + x.getCustomerTypeId());
          log.info("TIPO CLIENTE: " + x.getCustomerTypeId());
          if (x.getCustomerTypeId().equals("PER")) {
            // System.out.println("Cuenta Personal");
            log.info("Cuenta Personal");
            if (ahorros.size() <= 1 && corrientes.size() <= 1 && plazosFijo.size() <= 1) {
              isValid.set(true);

              // System.out.println("isValid:" + isValid.get());
              log.info("isValid:" + isValid.get());
            }
          } else if (x.getCustomerTypeId().equals("EMP")) {
            // System.out.println("Cuenta Empresarial");
            log.info("Cuenta Empresarial");
            if (!bankAccount.getAccountTypeId().equals(1)
                || !bankAccount.getAccountTypeId().equals(3)) {
              isValid.set(true);
              // log.info("isValid:" + isValid.get());
              System.out.println("isValid:" + isValid.get());
            }
          }
          log.info("FIN validateSave() retorna: " + isValid.get());
        });
    return Mono.just(isValid.get());
  }

  /*private Boolean validateAccountNumber(BankAccount bankAccount) {
  log.info("INICIO validateAccountNumber()");
  log.info("bankAccount Document Number: " + bankAccount.getDocumentNumber());

  Boolean isValid = false;
  log.info("isValid: " + isValid);
  Mono<Customer> customer = customerService.findByDocumentNumber(bankAccount.getDocumentNumber()).flatMap(
          customer1 -> {
              log.info("Mono customerData: " + customer1);
              Flux<BankAccount> bankAccounts = getAllBankAccountsByCustomer(bankAccount.getDocumentNumber());
              bankAccounts.count().flatMap(
                      bankAccountList -> {
                          Integer accountNumber = bankAccountList.intValue();
                          System.out.println("NUMERO DE FILAS RETORNADAS = " + accountNumber.intValue());
                          return Mono.empty();
                      }
              );
              return Mono.empty();
          }
  );


  /*Flux<BankAccount> bankAccounts = getAllBankAccountsByCustomer(bankAccount.getDocumentNumber());
  bankAccounts.count().subscribe(x -> {
      System.out.println("NUMERO DE FILAS RETORNADAS = " + x.intValue());
  });*/
  /*bankAccounts.subscribe(x -> {
  log.info("N° bankAccounts: " + bankAccounts.count());
  System.out.println("LISTAS");
  Flux<BankAccount> savingsAccounts = bankAccounts.filter(account -> account.getAccountTypeId().equals(1)); //AHORRO
  Flux<BankAccount> checkingAccounts = bankAccounts.filter(account -> account.getAccountTypeId().equals(2)); //CORRIENTE
  Flux<BankAccount> fixedTermAccounts = bankAccounts.filter((account -> account.getAccountTypeId().equals(3))); //PLAZO FIJO

  System.out.println("CONTEOS");
  Mono<Long> savingsAccountLong = savingsAccounts.count();
  Mono<Long> checkingAccountLong = checkingAccounts.count();
  Mono<Long> fixedTermAccountLong = fixedTermAccounts.count();*/

  /*Long savingsAccountLong = Long.parseLong(savingsAccounts.count().toString());
  Long checkingAccountLong = Long.parseLong(checkingAccounts.count().toString());
  Long fixedTermAccountLong = Long.parseLong(fixedTermAccounts.count().toString());*/

  /*Integer savingsAccountQuantity = Math.toIntExact(savingsAccountLong.block());
          Integer checkingAccountQuantity = Math.toIntExact(checkingAccountLong.block());
          Integer fixedTermAccountQuantity = Math.toIntExact(fixedTermAccountLong.block());/*

          log.info("N° Cuentas de Ahorros: " + savingsAccountQuantity);
          log.info("N° Cuentas Corrientes: " + checkingAccountQuantity);
          log.info("N° Cuentas a Plazo Fijo: " + fixedTermAccountQuantity);



      if (customer.getCustomerTypeId().equals("PER")) {
          log.info("Cuenta Personal");
          if (savingsAccountQuantity <= 1 && checkingAccountQuantity <= 1 && fixedTermAccountQuantity <= 1) {
              isValid = true;
              log.info("isValid:" + isValid);
          }
      } else if (customer.getCustomerTypeId().equals("EMP")) {
          log.info("Cuenta Empresarial");
          if (!bankAccount.getAccountTypeId().equals(1) || !bankAccount.getAccountTypeId().equals(3)) {
              isValid = true;
              log.info("isValid:" + isValid);
          }
      }
      });


      log.info("FIN validateAccountNumber()");
      return isValid;
  }*/

  @Override
  public Mono<BankAccount> updateBankAccount(BankAccount bankAccount) {
    return bankAccountRepository.save(bankAccount);
  }

  @Override
  public Mono<Void> deleteBankAccount(ObjectId bankAccountId) {
    return bankAccountRepository.deleteById(bankAccountId);
  }

  @Override
  public Flux<BankAccount> getAllBankAccountsByCustomer(String documentNumber) {
    log.info("1 INICIO getAllBankAccountsByCustomer");
    log.info("2 documentNumber: " + documentNumber);
    Flux<BankAccount> bankAccounts = bankAccountRepository.findAll();
    bankAccounts.subscribe(
        x -> {
          log.info("3 lista bankAccounts");
        });
    // log.info("3 lista cantidad NO ESPERA: " +bankAccounts.count());
    // log.info("bankAccounts: " + bankAccounts.count());
    Flux<BankAccount> bankAccountsByDocNumber =
        bankAccounts.filter(x -> x.getDocumentNumber().equals(documentNumber));
    bankAccountsByDocNumber
        .count()
        .subscribe(
            x -> {
              // System.out.println("Cantidad de Cuentas Bancarias por DNI: " + x.intValue() + "
              // cuentas");
              log.info("4 Cantidad de Cuentas Bancarias por DNI: " + x.intValue() + " cuentas");
              // System.out.println("SALIENDO RECIËN CON EL VALOR OBTENIDO");
              log.info("5 SALIENDO RECIËN CON EL VALOR OBTENIDO");
              log.info("6 FIN getAllBankAccountsByCustomer");
            });
    // log.info("4 FIN getAllBankAccountsByCustomer NO ESPERA");
    return bankAccountsByDocNumber; // bankAccountRepository.findAll().subscribe();//.filter(bankAccount -> bankAccount.getCustomerId().equals(customerId)));
  }

  @Override
  public Mono<BankAccount> getBankAccount(ObjectId bankAccountId) {
    return bankAccountRepository.findById(bankAccountId);
  }
}
