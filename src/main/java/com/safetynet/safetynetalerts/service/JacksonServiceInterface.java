package com.safetynet.safetynetalerts.service;

import java.io.IOException;

public interface JacksonServiceInterface {
  /**
   * Save JSON data to a file
   *
   * @param <T>      type of data
   * @param filePath path to the file
   * @param data     data to save
   * @throws IOException if an I/O error occurs
   */
  <T> void saveToFile(String filePath, T data);

  /**
   * Load JSON data from a file
   *
   * @param <T>       type of data
   * @param filePath  path to the file
   * @param valueType class of the data
   * @return data loaded from the file
   * @throws RuntimeException if an I/O error occurs
   */
  <T> T loadFromFile(String filePath, Class<T> valueType);
}
