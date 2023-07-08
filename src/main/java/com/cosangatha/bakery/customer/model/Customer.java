package com.cosangatha.bakery.customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

//@Builder
@Getter
@Setter
@ToString
@Entity
//@NoArgsConstructor
@Table(name = "Customer")
public class Customer extends RepresentationModel<Customer> {

//        TODO : automated UUID generator
        @Id
        @Column(name="customer_id")
        private String id;

        private String name;

        @Column(name="email_address")
        private String emailAddress;

        private String address;

}
