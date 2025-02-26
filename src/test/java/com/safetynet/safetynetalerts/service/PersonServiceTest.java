package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.ChildAlertDTO.HouseholdMember;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.ReadMedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.ReadPersonRepository;
import com.safetynet.safetynetalerts.repository.WritePersonRepository;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PersonServiceTest {

  private Person child;
  private Person adult;
  private Person person1;
  private Person person2;

  @Mock
  private ReadPersonRepository readPersonRepository;

  @Mock
  private WritePersonRepository writePersonRepository;

  @Mock
  private ReadMedicalRecordRepository readMedicalRecordRepository;

  @Mock
  private CalculateAgeServiceInterface calculateAgeService;

  @InjectMocks
  private PersonService personService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    child = new Person();
    child.setFirstName("Child");
    child.setLastName("LastName");
    child.setAddress("123 Street");
    child.setCity("City");
    child.setZip("12345");
    child.setPhone("123-456-7890");
    child.setEmail("child@email.com");

    adult = new Person();
    adult.setFirstName("Adult");
    adult.setLastName("LastName");
    adult.setAddress("123 Street");
    adult.setCity("City");
    adult.setZip("12345");
    adult.setPhone("123-456-7891");
    adult.setEmail("adult@email.com");

    person1 = new Person();
    person1.setFirstName("FirstName1");
    person1.setLastName("Test");
    person1.setAddress("123 Street");
    person1.setCity("City");
    person1.setZip("12345");
    person1.setPhone("123-456-7890");
    person1.setEmail("person1@email.com");

    person2 = new Person();
    person2.setFirstName("FirstName2");
    person2.setLastName("Test");
    person2.setAddress("456 Street");
    person2.setCity("City");
    person2.setZip("12345");
    person2.setPhone("123-456-7891");
    person2.setEmail("person2@email.com");
  }

  @Nested
  class AllChildrenAtAddress {

    @Test
    void testAllChildrenAtAddress() {
      List<Person> personsAtAddress = Arrays.asList(child, adult);

      MedicalRecord childRecord = new MedicalRecord();
      when(readPersonRepository.findPersonsAtAddress("123 Street")).thenReturn(
        personsAtAddress
      );
      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(child)
      ).thenReturn(Optional.of(childRecord));
      when(calculateAgeService.calculate(any())).thenReturn(10);
      when(calculateAgeService.isChild(10)).thenReturn(true);

      List<ChildAlertDTO> result = personService.allChildrenAtAddress(
        "123 Street"
      );

      assertFalse(result.isEmpty());
      assertEquals(1, result.size());
      assertEquals("Child", result.get(0).getFirstName());
      assertEquals("LastName", result.get(0).getLastName());
      assertEquals(1, result.get(0).getHouseholdMembers().size());
    }

    @Test
    void testAllChildrenAtAddress_ChildrenNoFound() {
      List<Person> personsAtAddress = Arrays.asList(person1, person2);
      MedicalRecord adultRecord = new MedicalRecord();

      when(readPersonRepository.findPersonsAtAddress("123 Street")).thenReturn(
        personsAtAddress
      );
      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(person1)
      ).thenReturn(Optional.of(adultRecord));
      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(person2)
      ).thenReturn(Optional.of(adultRecord));
      when(calculateAgeService.calculate(any())).thenReturn(30);
      when(calculateAgeService.isChild(30)).thenReturn(false);

      List<ChildAlertDTO> result = personService.allChildrenAtAddress(
        "123 Street"
      );

      assertTrue(result.isEmpty());
    }
  }

  @Nested
  class GetPersonInfoByLastName {

    @Test
    void shouldReturnPersonInfoList() {
      List<Person> personsWithLastName = Arrays.asList(person1, person2);

      MedicalRecord medicalRecord1 = new MedicalRecord();
      MedicalRecord medicalRecord2 = new MedicalRecord();

      when(readPersonRepository.findPersonsWithLastName("Test")).thenReturn(
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
        "Test"
      );

      assertFalse(result.isEmpty());
      assertEquals(2, result.size());
      assertEquals("FirstName1", result.get(0).getFirstName());
      assertEquals("FirstName2", result.get(1).getFirstName());
    }

    @Test
    void whenNoPersonsFound() {
      when(
        readPersonRepository.findPersonsWithLastName("NoExisting")
      ).thenReturn(Collections.emptyList());

      List<PersonInfoDTO> result = personService.getPersonInfoByLastName(
        "NoExisting"
      );

      assertTrue(result.isEmpty());
    }

    @Test
    void whenNoMedicalRecordsFound() {
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
  }

  @Nested
  class GetEmailsByCity {

    @Test
    void shouldReturnEmails() {
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
    void whenNoEmailsFound() {
      final String city = "NonExistentCity";

      when(readPersonRepository.findEmailsByCity(city)).thenReturn(
        Collections.emptySet()
      );

      Set<String> result = personService.getEmailsByCity(city);

      assertTrue(result.isEmpty());
    }
  }

  @Nested
  class AddPerson {

    @Test
    void shouldCallRepositorySave() {
      Person person = new Person();
      person.setFirstName("John");
      person.setLastName("Doe");
      person.setAddress("123 Street");
      person.setCity("City");
      person.setZip("12345");
      person.setPhone("123-456-7890");
      person.setEmail("john@email.com");

      personService.addPerson(person);

      verify(writePersonRepository).save(person);
    }

    @Test
    void whenFirstNameIsNull() {
      Person person = new Person();
      person.setLastName("Doe");

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> personService.addPerson(person)
      );

      assertEquals(
        "First name and last name are required",
        exception.getMessage()
      );
    }

    @Test
    void whenLastNameIsNull() {
      Person person = new Person();
      person.setFirstName("John");

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> personService.addPerson(person)
      );

      assertEquals(
        "First name and last name are required",
        exception.getMessage()
      );
    }
  }

  @Nested
  class UpdatePerson {

    @Test
    void shouldCallRepositoryUpdate() {
      Person person = new Person();
      person.setFirstName("John");
      person.setLastName("Doe");
      person.setAddress("123 Street");
      person.setCity("City");
      person.setZip("12345");
      person.setPhone("123-456-7890");
      person.setEmail("john@email.com");

      personService.updatePerson(person);

      verify(writePersonRepository).update(person);
    }

    @Test
    void whenFirstNameIsNull() {
      Person person = new Person();
      person.setLastName("Doe");

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> personService.updatePerson(person)
      );

      assertEquals(
        "First name and last name are required",
        exception.getMessage()
      );
    }

    @Test
    void whenLastNameIsNull() {
      Person person = new Person();
      person.setFirstName("John");

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> personService.updatePerson(person)
      );

      assertEquals(
        "First name and last name are required",
        exception.getMessage()
      );
    }
  }

  @Nested
  class DeletePerson {

    @Test
    void shouldCallRepositoryDelete() {
      Person person = new Person();
      person.setFirstName("John");
      person.setLastName("Doe");

      personService.deletePerson(person);

      verify(writePersonRepository).delete(person);
    }

    @Test
    void whenFirstNameIsNull() {
      Person person = new Person();
      person.setLastName("Doe");

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> personService.deletePerson(person)
      );

      assertEquals(
        "First name and last name are required",
        exception.getMessage()
      );
    }

    @Test
    void whenLastNameIsNull() {
      Person person = new Person();
      person.setFirstName("John");

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> personService.deletePerson(person)
      );

      assertEquals(
        "First name and last name are required",
        exception.getMessage()
      );
    }
  }

  @Nested
  public class AllHouseholdMembers_PrivateMethod {

    @Test
    void allHouseholdMembers() throws Exception {
      Person targetChild = new Person();
      targetChild.setFirstName("TargetChild");
      targetChild.setLastName("Family");

      Person sameNamePerson = new Person();
      sameNamePerson.setFirstName("TargetChild");
      sameNamePerson.setLastName("Family");

      Person differentFirstName = new Person();
      differentFirstName.setFirstName("OtherChild");
      differentFirstName.setLastName("Family");

      Person differentLastName = new Person();
      differentLastName.setFirstName("TargetChild");
      differentLastName.setLastName("OtherFamily");

      Person completelyDifferent = new Person();
      completelyDifferent.setFirstName("Adult");
      completelyDifferent.setLastName("OtherFamily");

      List<Person> personsAtAddress = Arrays.asList(
        targetChild,
        sameNamePerson,
        differentFirstName,
        differentLastName,
        completelyDifferent
      );

      ChildAlertDTO childAlertDTO = new ChildAlertDTO();
      childAlertDTO.setFirstName("TargetChild");
      childAlertDTO.setLastName("Family");

      Method method =
        PersonService.class.getDeclaredMethod(
            "allHouseholdMembers",
            List.class,
            Person.class,
            ChildAlertDTO.class
          );
      method.setAccessible(true);

      ChildAlertDTO result = (ChildAlertDTO) method.invoke(
        personService,
        personsAtAddress,
        targetChild,
        childAlertDTO
      );

      assertEquals(3, result.getHouseholdMembers().size());

      List<String> householdFirstNames = result
        .getHouseholdMembers()
        .stream()
        .map(HouseholdMember::getFirstName)
        .collect(Collectors.toList());

      assertTrue(householdFirstNames.contains("OtherChild"));
      assertTrue(householdFirstNames.contains("TargetChild"));
      assertTrue(householdFirstNames.contains("Adult"));

      boolean hasExactDuplicate = result
        .getHouseholdMembers()
        .stream()
        .anyMatch(
          m ->
            "TargetChild".equals(m.getFirstName()) &&
            "Family".equals(m.getLastName())
        );

      assertFalse(hasExactDuplicate);
    }
  }
}
