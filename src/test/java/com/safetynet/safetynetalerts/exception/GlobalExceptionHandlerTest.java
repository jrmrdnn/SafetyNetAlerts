package com.safetynet.safetynetalerts.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler exceptionHandler;

  @Mock
  private MethodArgumentNotValidException methodArgumentNotValidException;

  @Mock
  private BindingResult bindingResult;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    exceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  public void testHandleRuntimeException() {
    RuntimeException exception = new RuntimeException("Test runtime exception");
    ResponseEntity<String> response = exceptionHandler.handleRuntimeException(
      exception
    );

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Unexpected error occurred", response.getBody());
  }

  @Test
  public void testHandleIllegalArgumentException() {
    IllegalArgumentException exception = new IllegalArgumentException(
      "Invalid argument"
    );
    ResponseEntity<String> response =
      exceptionHandler.handleIllegalArgumentException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid argument", response.getBody());
  }

  @Test
  public void testHandleValidationExceptions() {
    List<FieldError> fieldErrors = new ArrayList<>();
    fieldErrors.add(
      new FieldError("testObject", "field1", "must not be empty")
    );
    fieldErrors.add(
      new FieldError("testObject", "field2", "must be a valid email")
    );

    when(methodArgumentNotValidException.getBindingResult()).thenReturn(
      bindingResult
    );
    when(bindingResult.getAllErrors()).thenReturn(new ArrayList<>(fieldErrors));

    ResponseEntity<Map<String, String>> response =
      exceptionHandler.handleValidationExceptions(
        methodArgumentNotValidException
      );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Map<String, String> responseBody = response.getBody();
    assertNotNull(responseBody);
    assertTrue(responseBody.containsKey("field1"));
    assertTrue(responseBody.containsKey("field2"));
    assertEquals("must not be empty", responseBody.get("field1"));
    assertEquals("must be a valid email", responseBody.get("field2"));
  }

  @Test
  public void testHandleException() {
    Exception exception = new Exception("General exception");
    ResponseEntity<String> response = exceptionHandler.handleException(
      exception
    );

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Unexpected error occurred", response.getBody());
  }
}
