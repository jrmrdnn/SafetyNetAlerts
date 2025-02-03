package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.model.Person;

public interface PersonServiceInterface {

    /**
     * Get all persons from the JSON file
     * 
     * @return list of persons
     */
    List<Person> getAllPersons();

}