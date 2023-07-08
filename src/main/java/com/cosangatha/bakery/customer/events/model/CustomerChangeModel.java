package com.cosangatha.bakery.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerChangeModel {

    private String type;
    private String action;
    private String customerId;
    private String correlationId;

}
