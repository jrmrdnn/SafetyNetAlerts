package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhoneAlertServiceTest {

    @Mock
    private JsonWrapper jsonWrapper;

    @Mock
    private FireStationServiceInterface fireStationService;

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPhoneNumbersByFirestation() {
        String fireStationNumber = "1";

        List<String> addresses = Arrays.asList("123 Main St", "456 Elm St");

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("123 Main St");
        person1.setCity("City");
        person1.setZip("12345");
        person1.setPhone("123-456-7890");
        person1.setEmail("john@example.com");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress("456 Elm St");
        person2.setCity("City");
        person2.setZip("12345");
        person2.setPhone("987-654-3210");
        person2.setEmail("jane@example.com");

        Person person3 = new Person();
        person3.setFirstName("Jim");
        person3.setLastName("Beam");
        person3.setAddress("789 Oak St");
        person3.setCity("City");
        person3.setZip("12345");
        person3.setPhone("555-555-5555");
        person3.setEmail("jim@example.com");

        List<Person> persons = Arrays.asList(person1, person2, person3);

        when(fireStationService.getAddresses(fireStationNumber)).thenReturn(addresses);
        when(jsonWrapper.getPersons()).thenReturn(persons);

        Set<String> expectedPhoneNumbers = new HashSet<>(Arrays.asList("123-456-7890", "987-654-3210"));
        Set<String> actualPhoneNumbers = phoneAlertService.getPhoneNumbersByFirestation(fireStationNumber);

        assertEquals(expectedPhoneNumbers, actualPhoneNumbers);
    }
}