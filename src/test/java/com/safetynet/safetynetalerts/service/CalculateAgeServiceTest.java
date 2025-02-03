package com.safetynet.safetynetalerts.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CalculateAgeServiceTest {

    @Autowired
    private CalculateAgeService calculateAgeService;

    @Test
    public void testCalculateAge() {
        String birthdate = "01/01/2000";
        int expectedAge = LocalDate.now().getYear()
                - LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy")).getYear();
        int actualAge = calculateAgeService.calculate(birthdate);
        assertEquals(expectedAge, actualAge);
    }

    @Test
    public void testIsAdult() {
        assertTrue(calculateAgeService.isAdult(20));
        assertFalse(calculateAgeService.isAdult(18));
    }

    @Test
    public void testIsChild() {
        assertTrue(calculateAgeService.isChild(18));
        assertFalse(calculateAgeService.isChild(19));
    }
}