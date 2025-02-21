package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommunityEmailService implements CommunityEmailServiceInterface {

    @Autowired
    private PersonServiceInterface personService;

    @Override
    public Set<String> getEmailsByCity(String city) {
        return personService.getAllPersons().stream().filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail).collect(Collectors.toSet());
    }
}
