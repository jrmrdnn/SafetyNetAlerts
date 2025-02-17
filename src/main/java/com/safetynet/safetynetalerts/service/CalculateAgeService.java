package com.safetynet.safetynetalerts.service;

import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;

@Service
public class CalculateAgeService implements CalculateAgeServiceInterface {

    @Override
    public int calculate(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    @Override
    public boolean isChild(int age) {
        return age <= 18;
    }
}