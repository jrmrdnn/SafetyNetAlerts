package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CalculateAgeServiceTest {

  private CalculateAgeService calculateAgeService;

  @BeforeEach
  public void setUp() {
    calculateAgeService = new CalculateAgeService();
  }

  @Nested
  class Calculate {

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
    public void testCalculate_WithNull() {
      assertThrows(NullPointerException.class, () ->
        calculateAgeService.calculate(null)
      );
    }
  }

  @Nested
  class IsChild {

    @Test
    public void testIsChild_True() {
      int age18 = 18;
      int age10 = 10;
      int nimValue = Integer.MIN_VALUE;

      assertTrue(calculateAgeService.isChild(age18));
      assertTrue(calculateAgeService.isChild(age10));
      assertTrue(calculateAgeService.isChild(nimValue));
    }

    @Test
    public void testIsChild_False() {
      int age19 = 19;
      int age100 = 100;
      int maxValue = Integer.MAX_VALUE;

      assertFalse(calculateAgeService.isChild(age19));
      assertFalse(calculateAgeService.isChild(age100));
      assertFalse(calculateAgeService.isChild(maxValue));
    }
  }
}
