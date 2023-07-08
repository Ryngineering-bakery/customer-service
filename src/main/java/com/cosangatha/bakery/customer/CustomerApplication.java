package com.cosangatha.bakery.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
// exposes refresh endpoint to refresh custom configurations.
@RefreshScope

// DEprecated in Spring cloud stream 4.0.0..
//@EnableBinding(Source.class)
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
