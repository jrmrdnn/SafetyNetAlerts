package com.safetynet.safetynetalerts.service;

public interface CalculateAgeServiceInterface {
  /**
   * Calculates the age from a given birthdate.
   *
   * @param birthdate the birthdate in the format "MM/dd/yyyy"
   * @return the calculated age in years
   */
  int calculate(String birthdate);

  /**
   * Checks if a person is a child based on their age.
   *
   * @param age the age of the person
   * @return true if the person is a child, false otherwise
   */
  boolean isChild(int age);
}
