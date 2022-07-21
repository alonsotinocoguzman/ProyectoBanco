package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Model.Entity.Customer;
import com.project.bank.ProjectBank.Model.Service.CustomerService;
import com.project.bank.ProjectBank.Utils.UIUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UIUtils.BASEURL)
@Slf4j
@AllArgsConstructor
public class CustomerController {
  private final CustomerService customerService;

  @GetMapping(UIUtils.CUSTOMER_ALL)
  public Flux<Customer> getAllCustomer() {
    log.info("Ingreso a getAllCustomer");
    return customerService.findAll();
  }

  @GetMapping(UIUtils.CUSTOMER_ID)
  public Mono<Customer> getCustomerByDocumentNumber(@PathVariable(value = "documentNumber") String documentNumber) {
    log.info("Ingreso a getCustomerByDocumentNumber");
    return customerService.findByDocumentNumber(documentNumber);
  }

  @PostMapping(UIUtils.CUSTOMER_INS)
  public Mono<Customer> saveCustomer(@RequestBody Customer customer){
    log.info("Ingreso a saveCustomer");
    return customerService.saveCustomer(customer);
  }

  @PutMapping(UIUtils.CUSTOMER_UPD)
  public Mono<Customer> updateCustomer(@RequestBody Customer customer){
    log.info("Ingreso a updateCustomer");
    return customerService.updateCustomer(customer);
  }

  @DeleteMapping(UIUtils.CUSTOMER_DEL)
  public Mono<Void> deleteCustomer(@PathVariable(value = "customerId") ObjectId customerId){
    log.info("Ingreso a deleteCustomer");
    return customerService.deleteCustomer(customerId);
  }
  @GetMapping(UIUtils.CUSTOMER_ALL_PRODUCTS)
  public Flux<Object> getCustomerAllProucts(@RequestParam(value = "nroDocumento") String nroDocumento){
    return customerService.getCustomerAllProucts(nroDocumento);
  }
}
