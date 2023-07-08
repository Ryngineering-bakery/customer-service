package com.cosangatha.bakery.customer.service;

import com.cosangatha.bakery.customer.config.CustomerConfig;
import com.cosangatha.bakery.customer.events.source.SimpleSourceBean;
import com.cosangatha.bakery.customer.repository.CustomerRepository;
import com.cosangatha.bakery.customer.model.Customer;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerConfig config;

    private final SimpleSourceBean source;

    private final Tracer tracer;

//    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

//    Supplier<Customer> newDummy = () -> Customer.builder().id("1").name("Test").emailAddress("Test@Gmail.com").address("XYZ Street").build();


    public Customer getCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("No Customer Found"));

    }

    public String createCustomer(Customer customer) {
        log.info("Config Source : {0} , Creating a customer and logging with SLF4j" , config.getConfigSource() );
        customer.setId(UUID.randomUUID().toString());
        Customer persistedCustomer = customerRepository.save(customer);
        source.publishCustomerChange("CREATE", persistedCustomer.getId());
        new ArrayList<>().stream().collect(Collectors.toList());
        return persistedCustomer.getId();
    }

    public Customer updateCustomer(String customerId, Customer toUpdate) {
        log.info("[ ] Updating a customer for customerId ");
        //        Create a span .. If a span is present in the Thread it will be the parent of "cacheCustomerSpan" span
        Span cacheCustomerSpan = this.tracer.nextSpan().name("updateCustomerInformation");
        //        Start a span and put it in scope.. Putting in scope means the span is in thread local and added to MDC to contain tracing information.
        this.tracer.withSpan(cacheCustomerSpan.start());
        try {
            Customer modifiedCustomer = customerRepository.findById(customerId)
                    .map(customer -> {
                        customer.setName(toUpdate.getName());
                        customer.setEmailAddress(toUpdate.getEmailAddress());
                        customer.setAddress(toUpdate.getAddress());
                        return customer;
                    }).orElseThrow(() -> new IllegalArgumentException("No Customer Found"));
            Customer customerFromDB = customerRepository.save(modifiedCustomer);
            source.publishCustomerChange("UPDATE", customerFromDB.getId());
            return customerFromDB;
        } finally {
//            tag a  span for debugging
            cacheCustomerSpan.tag("peer.service","postgres");
//            log an event on a span
            cacheCustomerSpan.event("Customer information updated");
//            end span
            cacheCustomerSpan.end();
        }

    }


    public void deleteCustomer(String customerId){
        Customer toDelete = new Customer();
         toDelete.setId(customerId);
        customerRepository.delete(toDelete);
        source.publishCustomerChange("DELETE", customerId);
        log.info("[ ] Deleting a customer");
    }

}
