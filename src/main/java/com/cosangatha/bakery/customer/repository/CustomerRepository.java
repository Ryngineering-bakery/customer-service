package com.cosangatha.bakery.customer.repository;

import com.cosangatha.bakery.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,String> {
    public Customer findByEmailAddress(String emailAddress);
}
