package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.ReadMedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.ReadPersonRepository;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PersonServiceTest {

  @Mock
  private ReadPersonRepository readPersonRepository;

  @Mock
  private ReadMedicalRecordRepository readMedicalRecordRepository;

  @Mock
  private CalculateAgeServiceInterface calculateAgeService;

  @InjectMocks
  private PersonService personService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void allChildrenAtAddress_ShouldReturnChildrenWithHouseholdMembers() {
    final String address = "123 Street";

    Person child = new Person();
    child.setFirstName("Child");
    child.setLastName("Test");
    child.setAddress(address);
    child.setCity("City");
    child.setZip("12345");
    child.setPhone("123-456-7890");
    child.setEmail("child@email.com");

    Person adult = new Person();
    adult.setFirstName("Adult");
    adult.setLastName("Test");
    adult.setAddress(address);
    adult.setCity("City");
    adult.setZip("12345");
    adult.setPhone("123-456-7891");
    adult.setEmail("adult@email.com");

    List<Person> personsAtAddress = Arrays.asList(child, adult);

    MedicalRecord childRecord = new MedicalRecord();
    when(readPersonRepository.findPersonsAtAddress(address)).thenReturn(
      personsAtAddress
    );
    when(
      readMedicalRecordRepository.findByFirstNameAndLastName(child)
    ).thenReturn(Optional.of(childRecord));
    when(calculateAgeService.calculate(any())).thenReturn(10);
    when(calculateAgeService.isChild(10)).thenReturn(true);

    List<ChildAlertDTO> result = personService.allChildrenAtAddress(address);

    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals("Child", result.get(0).getFirstName());
    assertEquals(1, result.get(0).getHouseholdMembers().size());
  }

  @Test
  void getPersonInfoByLastName_ShouldReturnPersonInfoList() {
    final String lastName = "Test";

    Person person1 = new Person();
    person1.setFirstName("FirstName1");
    person1.setLastName(lastName);
    person1.setAddress("123 Street");
    person1.setCity("City");
    person1.setZip("12345");
    person1.setPhone("123-456-7890");
    person1.setEmail("person1@email.com");

    Person person2 = new Person();
    person2.setFirstName("FirstName2");
    person2.setLastName(lastName);
    person2.setAddress("456 Street");
    person2.setCity("City");
    person2.setZip("12345");
    person2.setPhone("123-456-7891");
    person2.setEmail("person2@email.com");

    List<Person> personsWithLastName = Arrays.asList(person1, person2);

    MedicalRecord medicalRecord1 = new MedicalRecord();
    MedicalRecord medicalRecord2 = new MedicalRecord();

    when(readPersonRepository.findPersonsWithLastName(lastName)).thenReturn(
      personsWithLastName
    );
    when(
      readMedicalRecordRepository.findByFirstNameAndLastName(person1)
    ).thenReturn(Optional.of(medicalRecord1));
    when(
      readMedicalRecordRepository.findByFirstNameAndLastName(person2)
    ).thenReturn(Optional.of(medicalRecord2));
    when(calculateAgeService.calculate(any())).thenReturn(30);

    List<PersonInfoDTO> result = personService.getPersonInfoByLastName(
      lastName
    );

    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
    assertEquals("FirstName1", result.get(0).getFirstName());
    assertEquals("FirstName2", result.get(1).getFirstName());
  }

  @Test
  void getPersonInfoByLastName_ShouldReturnEmptyList_WhenNoPersonsFound() {
    final String lastName = "NonExistent";

    when(readPersonRepository.findPersonsWithLastName(lastName)).thenReturn(
      Collections.emptyList()
    );

    List<PersonInfoDTO> result = personService.getPersonInfoByLastName(
      lastName
    );

    assertTrue(result.isEmpty());
  }

  @Test
  void getPersonInfoByLastName_ShouldReturnEmptyList_WhenNoMedicalRecordsFound() {
    final String lastName = "Test";

    Person person = new Person();
    person.setFirstName("FirstName");
    person.setLastName(lastName);
    person.setAddress("123 Street");
    person.setCity("City");
    person.setZip("12345");
    person.setPhone("123-456-7890");
    person.setEmail("person@email.com");

    List<Person> personsWithLastName = Collections.singletonList(person);

    when(readPersonRepository.findPersonsWithLastName(lastName)).thenReturn(
      personsWithLastName
    );
    when(
      readMedicalRecordRepository.findByFirstNameAndLastName(person)
    ).thenReturn(Optional.empty());

    List<PersonInfoDTO> result = personService.getPersonInfoByLastName(
      lastName
    );

    assertTrue(result.isEmpty());
  }

  @Test
  void getEmailsByCity_ShouldReturnEmails() {
    final String city = "City";

    Set<String> emails = new HashSet<>(
      Arrays.asList("email1@example.com", "email2@example.com")
    );

    when(readPersonRepository.findEmailsByCity(city)).thenReturn(emails);

    Set<String> result = personService.getEmailsByCity(city);

    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
    assertTrue(result.contains("email1@example.com"));
    assertTrue(result.contains("email2@example.com"));
  }

  @Test
  void getEmailsByCity_ShouldReturnEmptySet_WhenNoEmailsFound() {
    final String city = "NonExistentCity";

    when(readPersonRepository.findEmailsByCity(city)).thenReturn(
      Collections.emptySet()
    );

    Set<String> result = personService.getEmailsByCity(city);

    assertTrue(result.isEmpty());
  }
}
