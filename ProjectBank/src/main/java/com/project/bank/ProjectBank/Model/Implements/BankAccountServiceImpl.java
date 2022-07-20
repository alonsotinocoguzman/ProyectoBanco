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
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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
    public Mono<BankAccount> saveBankAccount(BankAccount bankAccount) {
        log.info("INICIO saveBankAccountImpl");

        Mono<BankAccount> bankAccountSaved = validateSave(bankAccount).flatMap(result -> {
            log.info("Resultado de la Validación -> " + result.booleanValue());

            if (result.booleanValue()) {
                log.info("FIN saveBankAccountImpl");
                log.info("FIN saveBankAccount");
                return bankAccountRepository.save(bankAccount);
            } else {
                log.info("bankAccountSaved FALLIDO");
                return Mono.empty();
            }
        });
        return bankAccountSaved;
    }

    private Mono<Boolean> validateSave(BankAccount bankAccount) {
        log.info("INICIO validateSave()");
        log.info("Número de Documento de la Cuenta -> " + bankAccount.getDocumentNumber());

        AtomicReference<Boolean> isValid = new AtomicReference<>(false);
        log.info("isValid: " + isValid);

        Mono<Customer> customerMono = customerService.findByDocumentNumber(bankAccount.getDocumentNumber());
        Mono<Boolean> isValidated = customerMono.flatMap(x -> {
            log.info("Datos de Cliente: " + x.getName() + " " + x.getLastName() + " | " + x.getDni());

            Flux<BankAccount> bankAccountList = getAllBankAccountsByCustomer(x.getDni());
            List<BankAccount> bankAccounts = bankAccountList.toStream().collect(Collectors.toList());

            Integer intBankAccounts = Math.toIntExact(bankAccounts.stream().count());
            log.info("Cuentas en Total -> " + intBankAccounts);

            List<BankAccount> ahorros = bankAccounts.stream().filter(y -> y.getAccountTypeId().equals(1)).collect(Collectors.toList());
            log.info("Cuentas de Ahorros -> " + ahorros.size());

            List<BankAccount> corrientes = bankAccounts.stream().filter(y -> y.getAccountTypeId().equals(2)).collect(Collectors.toList());
            log.info("Cuentas Corrientes -> " + corrientes.size());

            List<BankAccount> plazosFijo = bankAccounts.stream().filter(y -> y.getAccountTypeId().equals(3)).collect(Collectors.toList());
            log.info("Cuentas a Plazo Fijo -> " + plazosFijo.size());

            log.info("Tipo Cliente: " + x.getCustomerTypeId());
            if (x.getCustomerTypeId().equals("PER")) {
                log.info("Cuenta Personal");
                if (bankAccount.getAccountTypeId().equals(1) && ahorros.size() + 1 <= 1) {
                    isValid.set(true);
                    log.info("Tipo de Cuenta -> AHORROS | isValid: " + isValid.get());
                }
                if (bankAccount.getAccountTypeId().equals(2) && corrientes.size() + 1 <= 1) {
                    isValid.set(true);
                    log.info("Tipo de Cuenta -> CORRIENTE | isValid: " + isValid.get());
                }
                if (bankAccount.getAccountTypeId().equals(3) && plazosFijo.size() + 1 <= 1) {
                    isValid.set(true);
                    log.info("Tipo de Cuenta -> A PLAZO FIJO | isValid: " + isValid.get());
                }
            } else if (x.getCustomerTypeId().equals("EMP")) {
                log.info("Cuenta Empresarial");
                if (bankAccount.getAccountTypeId().equals(1)) {
                    isValid.set(false);
                    log.info("Tipo de Cuenta -> AHORROS | isValid: " + isValid.get());
                }
                if (bankAccount.getAccountTypeId().equals(2)) {
                    isValid.set(true);
                    log.info("Tipo de Cuenta -> CORRIENTE | isValid: " + isValid.get());
                }
                if (bankAccount.getAccountTypeId().equals(3)) {
                    isValid.set(false);
                    log.info("Tipo de Cuenta -> A PLAZO FIJO | isValid: " + isValid.get());
                }
            }
            log.info("FIN validateSave() retorna -> " + isValid.get());
            return Mono.just(isValid.get());
        });
        return isValidated;
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

    @Override
    public Flux<BankAccount> getAllBankAccountsByCustomer(String documentNumber) {
        Flux<BankAccount> bankAccounts = bankAccountRepository.findAll();
        Flux<BankAccount> bankAccountsByDocNumber = bankAccounts.filter(x -> x.getDocumentNumber().equals(documentNumber));
        bankAccountsByDocNumber.count().subscribe();

        return bankAccountsByDocNumber;
    }

    @Override
    public Mono<BankAccount> updateBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public Mono<Void> deleteBankAccount(ObjectId bankAccountId) {
        return bankAccountRepository.deleteById(bankAccountId);
    }

    @Override
    public Mono<BankAccount> getBankAccount(ObjectId bankAccountId) {
        return bankAccountRepository.findById(bankAccountId);
    }
}