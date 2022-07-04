package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Entity.Customer;
import com.project.bank.ProjectBank.Service.CustomerService;
import com.project.bank.ProjectBank.Utils.UIUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
  public Mono<Customer> getCustomerById(@PathVariable(value = "customerId") ObjectId customerId) {
    log.info("Ingreso a getCustomerById");
    return customerService.findById(customerId);
  }

  @PostMapping(UIUtils.CUSTOMER_INS)
  public Flux<Customer> saveCustomer(@RequestBody Flux<Customer> customer){
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
}
