package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements PersonServiceInterface {

    @Autowired
    private JsonWrapper jsonWrapper;

    /**
     * Get all persons from the JSON file
     * 
     * @return list of persons
     */
    @Override
    public List<Person> getAllPersons() {
        return jsonWrapper.getPersons();
    }
}
