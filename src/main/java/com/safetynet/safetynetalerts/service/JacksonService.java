package com.safetynet.safetynetalerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JacksonService {

    private final ObjectMapper objectMapper;

    public JacksonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Save JSON data to a file
     * 
     * @param <T>      type of data
     * @param filePath path to the file
     * @param data     data to save
     * @throws IOException if an I/O error occurs
     */
    public <T> void saveToFile(String filePath, T data) throws IOException {
        objectMapper.writeValue(new File(filePath), data);
    }

    /**
     * Load JSON data from a file
     * 
     * @param <T>       type of data
     * @param filePath  path to the file
     * @param valueType class of the data
     * @return data loaded from the file
     * @throws RuntimeException if an I/O error occurs
     */
    public <T> T loadFromFile(String filePath, Class<T> valueType) {
        try {
            return objectMapper.readValue(new File(filePath), valueType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load data from file", e);
        }
    }
}
