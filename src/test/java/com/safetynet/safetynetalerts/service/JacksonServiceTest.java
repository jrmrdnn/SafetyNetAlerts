package com.safetynet.safetynetalerts.service;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JacksonServiceTest {

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private JacksonService jacksonService;

  @BeforeEach
  public void setUp() {
    jacksonService = new JacksonService(objectMapper);
  }

  @Test
  public void testSaveToFile() throws IOException {
    String filePath = "test.json";
    Object data = new Object();

    doNothing().when(objectMapper).writeValue(new File(filePath), data);

    jacksonService.saveToFile(filePath, data);

    verify(objectMapper, times(1)).writeValue(new File(filePath), data);
  }

  @Test
  public void testSaveToFileThrowsIOException() throws IOException {
    String filePath = "test.json";
    Object data = new Object();

    doThrow(new IOException())
      .when(objectMapper)
      .writeValue(new File(filePath), data);

    try {
      jacksonService.saveToFile(filePath, data);
    } catch (RuntimeException e) {
      System.out.println("testSaveToFileThrowsIOException passed");
    }

    verify(objectMapper, times(1)).writeValue(new File(filePath), data);
  }

  @Test
  public void testLoadFromFile() throws IOException {
    String filePath = "test.json";
    Class<Object> valueType = Object.class;
    Object data = new Object();

    when(objectMapper.readValue(new File(filePath), valueType)).thenReturn(
      data
    );

    jacksonService.loadFromFile(filePath, valueType);

    verify(objectMapper, times(1)).readValue(new File(filePath), valueType);
  }

  @Test
  public void testLoadFromFileThrowsIOException() throws IOException {
    String filePath = "test.json";
    Class<Object> valueType = Object.class;

    when(objectMapper.readValue(new File(filePath), valueType)).thenThrow(
      new IOException()
    );

    try {
      jacksonService.loadFromFile(filePath, valueType);
    } catch (RuntimeException e) {
      System.out.println("testLoadFromFileThrowsIOException passed");
    }

    verify(objectMapper, times(1)).readValue(new File(filePath), valueType);
  }
}
