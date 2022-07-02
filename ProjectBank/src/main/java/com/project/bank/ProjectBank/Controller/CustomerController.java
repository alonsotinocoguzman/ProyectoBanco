package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Entity.Customer;
import com.project.bank.ProjectBank.Service.CustomerService;
import com.project.bank.ProjectBank.Utils.UIUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UIUtils.BASEURL)
@Slf4j
@AllArgsConstructor
public class CustomerController {
  private final CustomerService customerService;

  @GetMapping(UIUtils.CUSTOMER_ALL)
  private Flux<Customer> getAllCustomer() {
    log.info("ingreso al hola mundo");
    return customerService.findAll();
  }
}
