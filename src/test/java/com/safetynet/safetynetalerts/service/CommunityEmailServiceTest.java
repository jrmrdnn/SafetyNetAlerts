package com.safetynet.safetynetalerts.service;

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
import java.util.Set;
import java.util.ArrayList;

public class CommunityEmailServiceTest {

    @InjectMocks
    private CommunityEmailService communityEmailService;

    @Mock
    private PersonServiceInterface personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEmailsByCity() {
        String city = "TestCity";
        Person person1 = new Person();
        person1.setCity(city);
        person1.setEmail("test1@example.com");

        Person person2 = new Person();
        person2.setCity(city);
        person2.setEmail("test2@example.com");

        Person person3 = new Person();
        person3.setCity("OtherCity");
        person3.setEmail("test3@example.com");

        List<Person> persons = Arrays.asList(person1, person2, person3);
        when(personService.getAllPersons()).thenReturn(persons);

        Set<String> emails = communityEmailService.getEmailsByCity(city);

        assertEquals(2, emails.size());
        List<String> emailList = new ArrayList<>(emails);
        assertEquals("test2@example.com", emailList.get(0));
        assertEquals("test1@example.com", emailList.get(1));
    }

    @Test
    public void testGetEmailsByCity_NoMatch() {
        String city = "NonExistentCity";
        Person person1 = new Person();
        person1.setCity("TestCity");
        person1.setEmail("test1@example.com");

        List<Person> persons = Arrays.asList(person1);
        when(personService.getAllPersons()).thenReturn(persons);

        Set<String> emails = communityEmailService.getEmailsByCity(city);

        assertEquals(0, emails.size());
    }
}