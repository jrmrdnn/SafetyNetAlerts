package com.safetynet.safetynetalerts.aspect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
public class LoggingAspectTest {

  private LoggingAspect loggingAspect;

  @Mock
  private Logger logger;

  @Mock
  private JoinPoint joinPoint;

  @Mock
  private ProceedingJoinPoint proceedingJoinPoint;

  @Mock
  private Signature signature;

  @Mock
  private Throwable throwable;

  @BeforeEach
  public void setUp() {
    loggingAspect = new LoggingAspect();
    loggingAspect.logger = logger;
  }

  @Test
  public void testLogBeforeController() {
    when(joinPoint.getSignature()).thenReturn(signature);
    when(signature.getName()).thenReturn("testMethod");

    loggingAspect.logBeforeController(joinPoint);

    verify(logger).debug(eq("🟧 Entering request: {}"), eq("testMethod"));
  }

  @Test
  public void testLogBeforeService() {
    when(joinPoint.getSignature()).thenReturn(signature);
    when(signature.getName()).thenReturn("testMethod");

    loggingAspect.logBeforeService(joinPoint);

    verify(logger).debug(eq("🔶 Entering service: {}"), eq("testMethod"));
  }

  @Test
  public void testLogAfterController() {
    when(joinPoint.getSignature()).thenReturn(signature);
    when(signature.getName()).thenReturn("testMethod");

    loggingAspect.logAfterController(joinPoint);

    verify(logger).debug(eq("🟦 Exiting request: {}"), eq("testMethod"));
  }

  @Test
  public void testLogAfterService() {
    when(joinPoint.getSignature()).thenReturn(signature);
    when(signature.getName()).thenReturn("testMethod");

    loggingAspect.logAfterService(joinPoint);

    verify(logger).debug(eq("🔷 Exiting service: {}"), eq("testMethod"));
  }

  @Test
  public void testLogAfterThrowing() {
    when(joinPoint.getSignature()).thenReturn(signature);
    when(signature.getName()).thenReturn("testMethod");
    when(throwable.getMessage()).thenReturn("Test error message");

    loggingAspect.logAfterThrowing(joinPoint, throwable);

    verify(logger).error(
      eq("🔴 Exception in method: {} with error: {}"),
      eq("testMethod"),
      eq("Test error message")
    );
  }

  @Test
  public void testLogExecutionTime() throws Throwable {
    when(proceedingJoinPoint.getSignature()).thenReturn(signature);
    when(signature.getName()).thenReturn("testMethod");
    Object expectedResult = new Object();
    when(proceedingJoinPoint.proceed()).thenReturn(expectedResult);

    Object result = loggingAspect.logExecutionTime(proceedingJoinPoint);

    verify(proceedingJoinPoint, times(1)).proceed();
    verify(logger).debug(
      eq("🚀 Method {} executed in {} ms"),
      eq("testMethod"),
      anyLong()
    );
    assertEquals(expectedResult, result);
  }
}
