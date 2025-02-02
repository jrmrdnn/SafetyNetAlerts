package com.safetynet.safetynetalerts.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.safetynetalerts.constants.JacksonConstants;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.service.JacksonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

  private static final Logger logger = LogManager.getLogger(
    JacksonConfig.class
  );

  /**
   * Configure Jackson service
   *
   * @param objectMapper Jackson object mapper
   * @return ObjectMapper
   */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(
      SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
      false
    );
    objectMapper.configure(
      DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
      false
    );
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    return objectMapper;
  }

  /**
   * Load JSON data from a file
   *
   * @param jsonService service to load JSON data
   * @return JSON data loaded from the file
   */
  @Bean
  public JsonWrapper jsonWrapper(JacksonService jsonService) {
    try {
      JsonWrapper jsonWrapper = jsonService.loadFromFile(
        JacksonConstants.FILE_PATH,
        JsonWrapper.class
      );
      logger.info("🟢 Successfully loaded JSON data from file");
      return jsonWrapper;
    } catch (Exception e) {
      logger.error("🔴 Impossible to load JSON data from file");
      throw new RuntimeException("Impossible to load JSON data from file");
    }
  }
}
