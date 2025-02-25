package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculateAgeServiceTest {

  private CalculateAgeService calculateAgeService;

  @BeforeEach
  public void setUp() {
    calculateAgeService = new CalculateAgeService();
  }

  @Test
  public void testCalculate() {
    String birthdate = "01/01/2000";
    int expectedAge =
      LocalDate.now().getYear() -
      LocalDate.parse(
        birthdate,
        DateTimeFormatter.ofPattern("MM/dd/yyyy")
      ).getYear();
    int actualAge = calculateAgeService.calculate(birthdate);
    assertEquals(expectedAge, actualAge);
  }

  @Test
  public void testIsChildTrue() {
    int age = 10;
    assertTrue(calculateAgeService.isChild(age));
  }

  @Test
  public void testIsChildFalse() {
    int age = 20;
    assertFalse(calculateAgeService.isChild(age));
  }
}
