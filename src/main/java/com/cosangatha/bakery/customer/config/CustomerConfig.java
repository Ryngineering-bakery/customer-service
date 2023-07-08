package com.cosangatha.bakery.customer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// NOTE: @Configuration is required to register the class as a bean.
@Configuration
@ConfigurationProperties(prefix = "example")
@Getter
@Setter
public class CustomerConfig {

    private String configSource;
}
