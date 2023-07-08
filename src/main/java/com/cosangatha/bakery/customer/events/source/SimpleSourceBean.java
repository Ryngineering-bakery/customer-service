package com.cosangatha.bakery.customer.events.source;

import com.cosangatha.bakery.customer.events.model.CustomerChangeModel;
import com.cosangatha.bakery.customer.util.UserContext;
import com.cosangatha.bakery.customer.util.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

// Source in the eventing architecture that serializes data and uses channel to send message to broker
// Implementation changed as spring stream 4.0.0
@Component
@RequiredArgsConstructor
@Slf4j
public class SimpleSourceBean {

    private final StreamBridge streamBridge;


    public void publishCustomerChange(String action, String customerId) {
      log.info("Sending Kafka message {} for customerId {}", action , customerId);
        CustomerChangeModel customerChangeModel = new CustomerChangeModel(CustomerChangeModel.class.getTypeName(),
                action,
                customerId,
                UserContextHolder.getContext().getCorrelationId());

// output-out-0 is the channel name , which would be used in the configuration file to define message broker queue names.
        streamBridge.send("output-out-0", MessageBuilder.withPayload(customerChangeModel).build());

    }

}
