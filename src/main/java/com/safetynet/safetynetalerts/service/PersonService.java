package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.constants.JacksonConstants;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements PersonServiceInterface {

    @Autowired
    private JsonWrapper jsonWrapper;

    @Autowired
    private JacksonServiceInterface jacksonService;

    /**
     * Get all persons from the JSON file
     * 
     * @return list of persons
     */
    @Override
    public List<Person> getAllPersons() {
        return jsonWrapper.getPersons();
    }

    /**
     * Add a person to the JSON file
     * 
     * @param person
     * @return the person added
     */
    @Override
    public Person addPerson(Person person) {
        List<Person> persons = jsonWrapper.getPersons();
        Optional<Person> existingPerson = getExistingPerson(person.getFirstName(), person.getLastName(), persons);

        if (existingPerson.isPresent()) {
            throw new IllegalArgumentException("Person already exists");
        }

        persons.add(person);
        jsonWrapper.setPersons(persons);
        jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
        return person;
    }

    /**
     * Update a person in the JSON file
     * 
     * @param updatedPerson
     * @return the updated person
     */
    @Override
    public Person updatePerson(Person person) {
        List<Person> persons = jsonWrapper.getPersons();
        Optional<Person> existingPerson = getExistingPerson(person.getFirstName(), person.getLastName(), persons);

        if (existingPerson.isPresent()) {
            updatePersonDetails(person, persons, existingPerson);
            jsonWrapper.setPersons(persons);
            jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
            return person;
        } else {
            throw new IllegalArgumentException("Person not found");
        }
    }

    /**
     * Delete a person from the JSON file
     * 
     * @param firstName
     * @param lastName
     */
    @Override
    public void deletePerson(Person person) {
        List<Person> persons = jsonWrapper.getPersons();
        Optional<Person> existingPerson = getExistingPerson(person.getFirstName(), person.getLastName(), persons);

        if (existingPerson.isPresent()) {
            persons.remove(existingPerson.get());
            jsonWrapper.setPersons(persons);
            jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
        } else {
            throw new IllegalArgumentException("Person not found");
        }
    }

    /**
     * Get an existing person from the list of persons
     * 
     * @param firstName
     * @param lastName
     * @param persons
     * @return the existing person
     */
    private Optional<Person> getExistingPerson(String firstName, String lastName, List<Person> persons) {
        Optional<Person> existingPerson = persons.stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName)
                        && person.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
        return existingPerson;
    }

    /**
     * Update the details of a person
     * 
     * @param person
     * @param persons
     * @param existingPerson
     */
    private void updatePersonDetails(Person person, List<Person> persons, Optional<Person> existingPerson) {
        String firstName = existingPerson.get().getFirstName();
        String lastName = existingPerson.get().getLastName();
        String address = existingPerson.get().getAddress();
        String city = existingPerson.get().getCity();
        String zip = existingPerson.get().getZip();
        String phone = existingPerson.get().getPhone();
        String email = existingPerson.get().getEmail();

        persons.remove(existingPerson.get());

        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(person.getAddress() == null ? address : person.getAddress());
        person.setCity(person.getCity() == null ? city : person.getCity());
        person.setZip(person.getZip() == null ? zip : person.getZip());
        person.setPhone(person.getPhone() == null ? phone : person.getPhone());
        person.setEmail(person.getEmail() == null ? email : person.getEmail());

        persons.add(person);
    }
}
