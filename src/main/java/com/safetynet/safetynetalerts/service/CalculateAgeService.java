package com.safetynet.safetynetalerts.service;

import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;

@Service
public class CalculateAgeService implements CalculateAgeServiceInterface {

    /**
     * Calculates the age from a given birthdate.
     *
     * @param birthdate the birthdate in the format "MM/dd/yyyy"
     * @return the calculated age in years
     */
    @Override
    public int calculate(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    /**
     * Checks if a person is an adult based on their age.
     *
     * @param age the age of the person
     * @return true if the person is an adult, false otherwise
     */
    @Override
    public boolean isAdult(int age) {
        return age > 18;
    }
}