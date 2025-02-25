package com.safetynet.safetynetalerts.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MedicalRecordRepositoryTest {

  private MedicalRecord medicalRecord;
  private Person person;

  @Mock
  private JsonWrapper jsonWrapper;

  @InjectMocks
  private MedicalRecordRepository medicalRecordRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");
    medicalRecord.setBirthdate("01-01-2000");
    medicalRecord.setMedications(Arrays.asList("medication1"));
    medicalRecord.setAllergies(Arrays.asList("allergy1"));

    person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
  }

  @Test
  void testFindByFirstNameAndLastName_Person() {
    when(jsonWrapper.getMedicalRecords()).thenReturn(
      Arrays.asList(medicalRecord)
    );

    Optional<MedicalRecord> result =
      medicalRecordRepository.findByFirstNameAndLastName(person);

    assertTrue(result.isPresent());
    assertEquals(medicalRecord, result.get());
  }
}
