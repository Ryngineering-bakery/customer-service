package com.cosangatha.bakery.customer.controller;

import com.cosangatha.bakery.customer.model.Customer;
import com.cosangatha.bakery.customer.service.CustomerService;
import com.cosangatha.bakery.customer.util.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value="v1/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") String customerId) {
        log.info("Correlation Id : {} " , UserContextHolder.getContext().getCorrelationId());
        Customer customer = customerService.getCustomer(customerId);
//        Create HATEOS links
        customer.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(CustomerController.class)
                .getCustomer(customerId)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CustomerController.class)
                        .createCustomer(null)).withRel("createCustomer"));
        return ResponseEntity.ok(customer);
    }


    @PutMapping(value = "/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") String customerId, @RequestBody Customer toUpdate) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, toUpdate);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody  Customer toCreate) {
        String customerId = customerService.createCustomer(toCreate);
        return ResponseEntity.created(URI.create("v1/customer" + customerId)).build();
    }
}
