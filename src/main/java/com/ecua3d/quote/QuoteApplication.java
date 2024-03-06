package com.ecua3d.quote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QuoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoteApplication.class, args);
	}

}
