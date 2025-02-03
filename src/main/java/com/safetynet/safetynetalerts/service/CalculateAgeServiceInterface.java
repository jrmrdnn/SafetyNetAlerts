package com.safetynet.safetynetalerts.service;

public interface CalculateAgeServiceInterface {

    int calculate(String birthdate);

    boolean isAdult(int age);
}