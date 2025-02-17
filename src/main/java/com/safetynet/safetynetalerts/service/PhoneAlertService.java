package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhoneAlertService implements PhoneAlertServiceInterface {

    @Autowired
    private JsonWrapper jsonWrapper;

    @Autowired
    private FireStationServiceInterface fireStationService;

    @Override
    public Set<String> getPhoneNumbersByFirestation(String fireStationNumber) {
        List<String> addresses = fireStationService.getAddresses(fireStationNumber);
        return fetchPhoneNumbersByAddress(addresses);
    }

    /**
     * Fetch phone numbers by address
     * 
     * @param addresses the addresses
     * @return a list of phone numbers
     */
    private Set<String> fetchPhoneNumbersByAddress(List<String> addresses) {
        Set<String> phoneNumbers = new HashSet<>();

        for (String address : addresses) {
            Set<String> phones = jsonWrapper.getPersons().stream().filter(person -> person.getAddress().equals(address))
                    .map(Person::getPhone).collect(Collectors.toSet());
            phoneNumbers.addAll(phones);
        }

        return phoneNumbers;
    }
}
