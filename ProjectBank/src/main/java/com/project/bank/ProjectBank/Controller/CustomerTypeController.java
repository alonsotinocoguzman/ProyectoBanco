package com.project.bank.ProjectBank.Controller;

import com.project.bank.ProjectBank.Entity.CustomerType;
import com.project.bank.ProjectBank.Service.CustomerTypeService;
import com.project.bank.ProjectBank.Utils.UIUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(UIUtils.CUSTOMERTYPE_BASEURL)
@Slf4j
@AllArgsConstructor
public class CustomerTypeController {
    private final CustomerTypeService customerTypeService;

    @PostMapping(UIUtils.CUSTOMERTYPE_INS)
    public Flux<CustomerType> saveCustomerTypes(@RequestBody List<CustomerType> customerTypes) {
        log.info("Ingreso a saveCustomerTypes");
        return customerTypeService.saveCustomerTypes(customerTypes);
    }

    @PutMapping(UIUtils.CUSTOMERTYPE_UPD)
    public Mono<CustomerType> updateCustomerType(@RequestBody CustomerType customerType) {
        log.info("Ingreso a updateCustomerType");
        return customerTypeService.updateCustomerType(customerType);
    }

    @DeleteMapping(UIUtils.CUSTOMERTYPE_DEL)
    public Mono<Void> deleteCustomerType(@PathVariable(value = "customerTypeId") ObjectId customerTypeId) {
        log.info("Ingreso a deleteCustomerType");
        return customerTypeService.deleteCustomerType(customerTypeId);
    }

    @GetMapping(UIUtils.CUSTOMERTYPE_ALL)
    public Flux<CustomerType> getAllCustomerTypes() {
        log.info("Ingreso a getAllCustomerTypes");
        return customerTypeService.getAllCustomerTypes();
    }

    @GetMapping(UIUtils.CUSTOMERTYPE_ID)
    public Mono<CustomerType> getCustomerType(@PathVariable(value = "customerTypeId") ObjectId customerTypeId) {
        log.info("Ingreso a getCustomerType");
        return customerTypeService.getCustomerTypeById(customerTypeId);
    }
}