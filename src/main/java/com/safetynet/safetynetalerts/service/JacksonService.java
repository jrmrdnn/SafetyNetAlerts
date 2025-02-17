package com.safetynet.safetynetalerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JacksonService implements JacksonServiceInterface {

    private static final Logger logger = LogManager.getLogger(JacksonService.class);

    private final ObjectMapper objectMapper;

    public JacksonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> void saveToFile(String filePath, T data) {
        try {
            objectMapper.writeValue(new File(filePath), data);
            logger.info("Data saved to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Failed to save data to file");
            throw new RuntimeException("Failed to save data to file", e);
        }
    }

    @Override
    public <T> T loadFromFile(String filePath, Class<T> valueType) {
        try {
            T data = objectMapper.readValue(new File(filePath), valueType);
            logger.info("Data loaded from file: " + filePath);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Failed to load data from file");
            throw new RuntimeException("Failed to load data from file", e);
        }
    }
}
