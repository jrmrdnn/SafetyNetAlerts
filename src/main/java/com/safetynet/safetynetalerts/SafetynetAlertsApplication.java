package com.safetynet.safetynetalerts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetynetAlertsApplication {

  private static final Logger logger = LogManager.getLogger(
    SafetynetAlertsApplication.class
  );

  public static void main(String[] args) {
    logger.info("Application started 🚀");
    SpringApplication.run(SafetynetAlertsApplication.class, args);
  }
}
