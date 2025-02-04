package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.safetynet.safetynetalerts.constants.JacksonConstants;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Mock
    private JsonWrapper jsonWrapper;

    @Mock
    private JacksonServiceInterface jacksonService;

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

    @Test
    public void testAddPerson() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("City");
        person.setZip("12345");
        person.setPhone("123-456-7890");
        person.setEmail("john.doe@example.com");

        List<Person> persons = new ArrayList<>();
        when(jsonWrapper.getPersons()).thenReturn(persons);

        Person addedPerson = personService.addPerson(person);

        assertEquals(person, addedPerson);
        verify(jsonWrapper).setPersons(anyList());
        verify(jacksonService).saveToFile(eq(JacksonConstants.FILE_PATH), any(JsonWrapper.class));
    }

    @Test
    public void testAddPersonAlreadyExists() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("City");
        person.setZip("12345");
        person.setPhone("123-456-7890");
        person.setEmail("john.doe@example.com");

        List<Person> persons = Arrays.asList(person);
        when(jsonWrapper.getPersons()).thenReturn(persons);

        assertThrows(IllegalArgumentException.class, () -> personService.addPerson(person));
    }

    @Test
    public void testUpdatePerson() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("City");
        person.setZip("12345");
        person.setPhone("123-456-7890");
        person.setEmail("john.doe@example.com");

        List<Person> persons = new ArrayList<>(Arrays.asList(person));
        when(jsonWrapper.getPersons()).thenReturn(persons);

        Person updatedPerson = new Person();
        updatedPerson.setFirstName("John");
        updatedPerson.setLastName("Doe");
        updatedPerson.setAddress("456 Elm St");
        updatedPerson.setCity("City");
        updatedPerson.setZip("12345");
        updatedPerson.setPhone("987-654-3210");
        updatedPerson.setEmail("john.doe@example.com");

        Person result = personService.updatePerson(updatedPerson);

        assertEquals(updatedPerson.getAddress(), result.getAddress());
        assertEquals(updatedPerson.getPhone(), result.getPhone());
        verify(jsonWrapper).setPersons(anyList());
        verify(jacksonService).saveToFile(eq(JacksonConstants.FILE_PATH), any(JsonWrapper.class));
    }

    @Test
    public void testUpdatePersonNotFound() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        List<Person> persons = Arrays.asList();
        when(jsonWrapper.getPersons()).thenReturn(persons);

        assertThrows(IllegalArgumentException.class, () -> personService.updatePerson(person));
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        List<Person> persons = new ArrayList<>(Arrays.asList(person));
        when(jsonWrapper.getPersons()).thenReturn(persons);

        personService.deletePerson(person);

        verify(jsonWrapper).setPersons(anyList());
        verify(jacksonService).saveToFile(eq(JacksonConstants.FILE_PATH), any(JsonWrapper.class));
    }

    @Test
    public void testDeletePersonNotFound() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        List<Person> persons = Arrays.asList();
        when(jsonWrapper.getPersons()).thenReturn(persons);

        assertThrows(IllegalArgumentException.class, () -> personService.deletePerson(person));
    }
}
