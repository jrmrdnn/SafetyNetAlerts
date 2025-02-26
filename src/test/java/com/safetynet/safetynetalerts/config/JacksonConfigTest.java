package com.safetynet.safetynetalerts.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.service.JacksonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JacksonConfigTest {

  @InjectMocks
  private JacksonConfig jacksonConfig;

  @Mock
  private JacksonService jacksonService;

  @BeforeEach
  public void setUp() {
    jacksonConfig = new JacksonConfig();
  }

  @Test
  public void testObjectMapper() {
    ObjectMapper objectMapper = jacksonConfig.objectMapper();
    assertNotNull(objectMapper);
    assertFalse(
      objectMapper
        .getSerializationConfig()
        .isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    );
    assertFalse(
      objectMapper
        .getDeserializationConfig()
        .isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    );
    assertTrue(
      objectMapper
        .getSerializationConfig()
        .isEnabled(SerializationFeature.INDENT_OUTPUT)
    );
  }

  @Test
  public void testJsonWrapper() throws Exception {
    JsonWrapper expectedJsonWrapper = new JsonWrapper();
    when(
      jacksonService.loadFromFile(anyString(), eq(JsonWrapper.class))
    ).thenReturn(expectedJsonWrapper);

    JsonWrapper jsonWrapper = jacksonConfig.jsonWrapper(jacksonService);
    assertNotNull(jsonWrapper);
    assertEquals(expectedJsonWrapper, jsonWrapper);
    verify(jacksonService, times(1)).loadFromFile(
      anyString(),
      eq(JsonWrapper.class)
    );
  }

  @Test
  public void testJsonWrapperException() throws Exception {
    when(
      jacksonService.loadFromFile(anyString(), eq(JsonWrapper.class))
    ).thenThrow(new RuntimeException("Test Exception"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      jacksonConfig.jsonWrapper(jacksonService);
    });

    assertEquals(
      "Impossible to load JSON data from file",
      exception.getMessage()
    );
    verify(jacksonService, times(1)).loadFromFile(
      anyString(),
      eq(JsonWrapper.class)
    );
  }
}
