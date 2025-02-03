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
import java.util.List;

public class PersonServiceTest {

    @Mock
    private JsonWrapper jsonWrapper;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPersons() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("123 Main St");
        person1.setCity("City");
        person1.setZip("12345");
        person1.setPhone("123-456-7890");
        person1.setEmail("john.doe@example.com");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress("456 Elm St");
        person2.setCity("City");
        person2.setZip("12345");
        person2.setPhone("987-654-3210");
        person2.setEmail("jane.doe@example.com");

        List<Person> expectedPersons = Arrays.asList(person1, person2);
        when(jsonWrapper.getPersons()).thenReturn(expectedPersons);

        List<Person> actualPersons = personService.getAllPersons();

        assertEquals(expectedPersons, actualPersons);
    }
}