package com.ms.finance_data_processing_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinanceDataProcessingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceDataProcessingServiceApplication.class, args);
	}

}
