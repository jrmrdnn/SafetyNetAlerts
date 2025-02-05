package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
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
}
