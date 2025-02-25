package com.safetynet.safetynetalerts.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(
    LoggingAspect.class
  );

  /**
   * Logs before a controller method is executed
   * @param joinPoint
   */
  @Before("execution(* com.safetynet.safetynetalerts.controller..*(..))")
  public void logBeforeController(JoinPoint joinPoint) {
    logger.debug("🟧 Entering request: {}", joinPoint.getSignature().getName());
  }

  /**
   * Logs before a service method is executed
   * @param joinPoint
   */
  @Before("execution(* com.safetynet.safetynetalerts.service..*(..))")
  public void logBeforeService(JoinPoint joinPoint) {
    logger.debug("🔶 Entering service: {}", joinPoint.getSignature().getName());
  }

  /**
   * Logs after a controller method is executed
   * @param joinPoint
   */
  @After("execution(* com.safetynet.safetynetalerts.controller..*(..))")
  public void logAfterController(JoinPoint joinPoint) {
    logger.debug("🟦 Exiting request: {}", joinPoint.getSignature().getName());
  }

  /**
   * Logs after a service method is executed
   * @param joinPoint
   */
  @After("execution(* com.safetynet.safetynetalerts.service..*(..))")
  public void logAfterService(JoinPoint joinPoint) {
    logger.debug("🔷 Exiting service: {}", joinPoint.getSignature().getName());
  }

  /**
   * Logs after an exception is thrown
   * @param joinPoint
   * @param error
   */
  @AfterThrowing(
    pointcut = "execution(* com.safetynet.safetynetalerts.controller..*(..)) || execution(* com.safetynet.safetynetalerts.service..*(..))",
    throwing = "error"
  )
  public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
    logger.error(
      "🔴 Exception in method: {} with error: {}",
      joinPoint.getSignature().getName(),
      error.getMessage()
    );
  }

  /**
   * Logs the execution time of a method
   * @param joinPoint
   * @return
   * @throws Throwable
   */
  @Around(
    "execution(* com.safetynet.safetynetalerts.controller..*(..)) || execution(* com.safetynet.safetynetalerts.service..*(..))"
  )
  public Object logExecutionTime(ProceedingJoinPoint joinPoint)
    throws Throwable {
    long start = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    long executionTime = System.currentTimeMillis() - start;
    logger.debug(
      "🚀 Method {} executed in {} ms",
      joinPoint.getSignature().getName(),
      executionTime
    );
    return proceed;
  }
}
