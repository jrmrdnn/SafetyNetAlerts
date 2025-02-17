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

    @Override
    public List<Person> getAllPersons() {
        return jsonWrapper.getPersons();
    }

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

    @Override
    public Person updatePerson(Person person) {
        List<Person> Allpersons = jsonWrapper.getPersons();
        Optional<Person> updatePerson = getExistingPerson(person.getFirstName(), person.getLastName(), Allpersons);

        if (updatePerson.isEmpty()) {
            throw new IllegalArgumentException("Person not found");
        }

        setUpdatePerson(person, Allpersons, updatePerson);
        jsonWrapper.setPersons(Allpersons);
        jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);

        return Allpersons.get(Allpersons.indexOf(updatePerson.get()));
    }

    @Override
    public void deletePerson(Person person) {
        List<Person> persons = jsonWrapper.getPersons();
        Optional<Person> existingPerson = getExistingPerson(person.getFirstName(), person.getLastName(), persons);

        if (existingPerson.isEmpty()) {
            throw new IllegalArgumentException("Person not found");
        }

        persons.remove(existingPerson.get());
        jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
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
     * Update a person in the list of persons
     * 
     * @param person       The new person data
     * @param persons      The list of persons
     * @param personUpdate The person to be modified
     */
    private void setUpdatePerson(Person person, List<Person> persons, Optional<Person> personUpdate) {
        personUpdate.stream().forEach(p -> {
            p.setAddress(person.getAddress() != null ? person.getAddress() : p.getAddress());
            p.setCity(person.getCity() != null ? person.getCity() : p.getCity());
            p.setZip(person.getZip() != null ? person.getZip() : p.getZip());
            p.setPhone(person.getPhone() != null ? person.getPhone() : p.getPhone());
            p.setEmail(person.getEmail() != null ? person.getEmail() : p.getEmail());
        });
        persons.set(persons.indexOf(personUpdate.get()), personUpdate.get());
    }
}
