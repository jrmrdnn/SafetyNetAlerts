package com.safetynet.safetynetalerts.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.DataPersistenceServiceInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PersonRepositoryTest {

  private Person person;

  @Mock
  private JsonWrapper jsonWrapper;

  @Mock
  private DataPersistenceServiceInterface dataPersistenceService;

  @InjectMocks
  private PersonRepository personRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    person.setAddress("123 Main St");
    person.setCity("Springfield");
    person.setZip("12345");
    person.setPhone("123-456-7890");
    person.setEmail("john@mail.com");
  }

  @Test
  void testFindPersonsByAddresses() {
    FireStation fireStation = new FireStation();
    fireStation.setAddress("123 Main St");

    when(jsonWrapper.getPersons()).thenReturn(
      Collections.singletonList(person)
    );

    List<Person> result = personRepository.findPersonsByAddresses(
      Collections.singletonList(fireStation)
    );

    assertEquals(1, result.size());
    assertEquals(person, result.get(0));
  }

  @Test
  void testFindPersonsAtAddress() {
    when(jsonWrapper.getPersons()).thenReturn(
      Collections.singletonList(person)
    );

    List<Person> result = personRepository.findPersonsAtAddress("123 Main St");

    assertEquals(1, result.size());
    assertEquals(person, result.get(0));
  }

  @Test
  void testFindPhoneNumbersByAddress() {
    FireStation fireStation = new FireStation();
    fireStation.setAddress("123 Main St");

    when(jsonWrapper.getPersons()).thenReturn(
      Collections.singletonList(person)
    );

    Set<String> result = personRepository.findPhoneNumbersByAddress(
      Collections.singletonList(fireStation)
    );

    assertEquals(1, result.size());
    assertTrue(result.contains("123-456-7890"));
  }

  @Test
  void testFindAndGroupPersonsByAddress() {
    Person person2 = new Person();
    person2.setFirstName("Jane");
    person2.setLastName("Doe");
    person2.setAddress("456 Elm St");
    person2.setCity("Springfield");
    person2.setZip("12345");
    person2.setPhone("987-654-3210");
    person2.setEmail("jane@mail.com");

    when(jsonWrapper.getPersons()).thenReturn(List.of(person, person2));

    Set<String> addresses = Set.of("123 Main St", "456 Elm St");

    Map<String, List<Person>> result =
      personRepository.findAndGroupPersonsByAddress(addresses);

    assertEquals(2, result.size());
    assertTrue(result.containsKey("123 Main St"));
    assertTrue(result.containsKey("456 Elm St"));
    assertEquals(1, result.get("123 Main St").size());
    assertEquals(person, result.get("123 Main St").get(0));
    assertEquals(1, result.get("456 Elm St").size());
    assertEquals(person2, result.get("456 Elm St").get(0));
  }

  @Test
  void testFindAndGroupPersonsByAddress_NoMatchingAddresses() {
    when(jsonWrapper.getPersons()).thenReturn(
      Collections.singletonList(person)
    );

    Set<String> addresses = Set.of("999 Unknown St");

    Map<String, List<Person>> result =
      personRepository.findAndGroupPersonsByAddress(addresses);

    assertTrue(result.isEmpty());
  }

  @Test
  void testFindPersonsWithLastName() {
    when(jsonWrapper.getPersons()).thenReturn(
      Collections.singletonList(person)
    );

    List<Person> result = personRepository.findPersonsWithLastName("Doe");

    assertEquals(1, result.size());
    assertEquals(person, result.get(0));
  }

  @Test
  void testFindEmailsByCity() {
    when(jsonWrapper.getPersons()).thenReturn(
      Collections.singletonList(person)
    );

    Set<String> result = personRepository.findEmailsByCity("Springfield");

    assertEquals(1, result.size());
    assertTrue(result.contains("john@mail.com"));
  }

  @Test
  void testSave() {
    List<Person> persons = new ArrayList<>();
    when(jsonWrapper.getPersons()).thenReturn(persons);

    personRepository.save(person);

    verify(jsonWrapper, times(2)).getPersons();
    verify(dataPersistenceService).saveData();
    assertTrue(persons.contains(person));
  }

  @Test
  void testUpdate() {
    Person updatedPerson = new Person();
    updatedPerson.setFirstName("John");
    updatedPerson.setLastName("Doe");
    updatedPerson.setAddress("456 Elm St");

    List<Person> persons = new ArrayList<>(Collections.singletonList(person));
    when(jsonWrapper.getPersons()).thenReturn(persons);

    personRepository.update(updatedPerson);

    verify(jsonWrapper, times(2)).getPersons();
    verify(dataPersistenceService).saveData();
    assertFalse(persons.contains(person));
  }

  @Test
  void testDelete() {
    List<Person> persons = new ArrayList<>(Collections.singletonList(person));
    when(jsonWrapper.getPersons()).thenReturn(persons);

    assertTrue(persons.contains(person));

    personRepository.delete(person);

    verify(jsonWrapper, times(2)).getPersons();
    verify(dataPersistenceService).saveData();
    assertFalse(persons.contains(person));
  }
}
