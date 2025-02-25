package com.safetynet.safetynetalerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

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
