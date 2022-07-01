package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Utils.UIUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UIUtils.BASEURL)
@AllArgsConstructor
public class CustomerController {

  // private final CustomerService customerService;

  @GetMapping(UIUtils.CUSTOMER_ALL)
  private Mono<String> getAllCustomer() {
    return Mono.just("hola mundo");
  }
}
