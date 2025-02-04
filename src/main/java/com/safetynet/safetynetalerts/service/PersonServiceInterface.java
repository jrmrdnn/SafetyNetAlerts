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

    /**
     * Add a person to the JSON file
     * 
     * @param person
     * @return the person added
     */
    Person addPerson(Person person);

    /**
     * Update a person in the JSON file
     * 
     * @param person
     * @return the updated person
     */
    Person updatePerson(Person person);

    /**
     * Delete a person from the JSON file
     * 
     * @param person
     */
    void deletePerson(Person person);

}