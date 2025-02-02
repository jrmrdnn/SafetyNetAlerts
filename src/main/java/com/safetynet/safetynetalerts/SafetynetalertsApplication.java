package com.safetynet.safetynetalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class SafetynetalertsApplication {

	private static final Logger logger = Logger.getLogger(SafetynetalertsApplication.class.getName());

	public static void main(String[] args) {
		logger.info("Application started 🚀");
		SpringApplication.run(SafetynetalertsApplication.class, args);
	}

}