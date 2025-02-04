package com.safetynet.safetynetalerts.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        logger.info("Request: POST /person");
        logger.info("Body: " + person);
        Person addedPerson = personService.addPerson(person);
        logger.info("Response: " + addedPerson);
        return addedPerson;
    }

    @PutMapping
    public Person updatePerson(@RequestBody Person person) {
        logger.info("Request: PUT /person");
        logger.info("Body: " + person);
        Person updatePerson = personService.updatePerson(person);
        logger.info("Response: " + updatePerson);
        return updatePerson;
    }

    @DeleteMapping
    public String deletePerson(@RequestBody Person person) {
        logger.info("Request: DELETE /person");
        logger.info("Body: " + person);
        personService.deletePerson(person);
        return "Person deleted successfully: " + person.getFirstName() + " " + person.getLastName();
    }
}
