package com.safetynet.safetynetalerts.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.DataPersistenceServiceInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MedicalRecordRepositoryTest {

  private MedicalRecord medicalRecord;
  private Person person;

  @Mock
  private JsonWrapper jsonWrapper;

  @Mock
  private DataPersistenceServiceInterface dataPersistenceService;

  @InjectMocks
  private MedicalRecordRepository medicalRecordRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");

    medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");
    medicalRecord.setBirthdate("01-01-2000");
    medicalRecord.setMedications(Arrays.asList("medication1"));
    medicalRecord.setAllergies(Arrays.asList("allergy1"));
  }

  @Nested
  class FindByFirstNameAndLastName {

    @Test
    void testFindByFirstNameAndLastName() {
      when(jsonWrapper.getMedicalRecords()).thenReturn(
        Arrays.asList(medicalRecord)
      );

      Optional<MedicalRecord> result =
        medicalRecordRepository.findByFirstNameAndLastName(person);

      assertTrue(result.isPresent());
      assertEquals(medicalRecord, result.get());
    }

    @Test
    void testFindByFirstNameAndLastName_PersonNotFound() {
      when(jsonWrapper.getMedicalRecords()).thenReturn(
        Arrays.asList(medicalRecord)
      );

      person.setFirstName("Jane");

      Optional<MedicalRecord> result =
        medicalRecordRepository.findByFirstNameAndLastName(person);

      assertTrue(result.isEmpty());
    }

    @Test
    void testFindByFirstNameAndLastName_NoMatchingPerson() {
      Person personNoMatchingFirstName = new Person();
      personNoMatchingFirstName.setFirstName("Jane");
      personNoMatchingFirstName.setLastName("Doe");

      Person personNoMatchingLastName = new Person();
      personNoMatchingLastName.setFirstName("John");
      personNoMatchingLastName.setLastName("Smith");

      when(jsonWrapper.getMedicalRecords()).thenReturn(
        Arrays.asList(medicalRecord)
      );

      Optional<MedicalRecord> resultNoMatchingFirstName =
        medicalRecordRepository.findByFirstNameAndLastName(
          personNoMatchingFirstName
        );

      assertTrue(resultNoMatchingFirstName.isEmpty());

      Optional<MedicalRecord> resultNoMatchingLastName =
        medicalRecordRepository.findByFirstNameAndLastName(
          personNoMatchingLastName
        );

      assertTrue(resultNoMatchingLastName.isEmpty());
    }

    @Test
    void testFindByFirstNameAndLastName_MedicalRecord() {
      when(jsonWrapper.getMedicalRecords()).thenReturn(
        Arrays.asList(medicalRecord)
      );
      Optional<MedicalRecord> result =
        medicalRecordRepository.findByFirstNameAndLastName(medicalRecord);
      assertTrue(result.isPresent());
      assertEquals(medicalRecord, result.get());
    }

    @Test
    void testFindByFirstNameAndLastName_MedicalRecordNotFound() {
      when(jsonWrapper.getMedicalRecords()).thenReturn(
        Arrays.asList(medicalRecord)
      );

      MedicalRecord medicalRecordNotFound = new MedicalRecord();
      medicalRecordNotFound.setFirstName("Jane");
      medicalRecordNotFound.setLastName("Doe");

      Optional<MedicalRecord> result =
        medicalRecordRepository.findByFirstNameAndLastName(
          medicalRecordNotFound
        );

      assertTrue(result.isEmpty());
    }

    @Test
    void testFindByFirstNameAndLastName_NoMatchingMedicalRecord() {
      MedicalRecord medicalRecordNoMatchingFirstName = new MedicalRecord();
      medicalRecordNoMatchingFirstName.setFirstName("Jane");
      medicalRecordNoMatchingFirstName.setLastName("Doe");

      MedicalRecord medicalRecordNoMatchingLastName = new MedicalRecord();
      medicalRecordNoMatchingLastName.setFirstName("John");
      medicalRecordNoMatchingLastName.setLastName("Smith");

      when(jsonWrapper.getMedicalRecords()).thenReturn(
        Arrays.asList(medicalRecord)
      );

      Optional<MedicalRecord> resultNoMatchingFirstName =
        medicalRecordRepository.findByFirstNameAndLastName(
          medicalRecordNoMatchingFirstName
        );

      assertTrue(resultNoMatchingFirstName.isEmpty());

      Optional<MedicalRecord> resultNoMatchingLastName =
        medicalRecordRepository.findByFirstNameAndLastName(
          medicalRecordNoMatchingLastName
        );

      assertTrue(resultNoMatchingLastName.isEmpty());
    }
  }

  @Nested
  class SaveMedicalRecord {

    @Test
    void testSave() {
      List<MedicalRecord> medicalRecords = new ArrayList<>();
      when(jsonWrapper.getMedicalRecords()).thenReturn(medicalRecords);

      medicalRecordRepository.save(medicalRecord);

      verify(jsonWrapper, times(2)).getMedicalRecords();
      verify(dataPersistenceService).saveData();
      assertTrue(medicalRecords.contains(medicalRecord));
    }

    @Test
    void testSave_ExistingMedicalRecord() {
      List<MedicalRecord> medicalRecords = new ArrayList<>(
        Collections.singletonList(medicalRecord)
      );

      when(jsonWrapper.getMedicalRecords()).thenReturn(medicalRecords);

      assertThrows(IllegalArgumentException.class, () ->
        medicalRecordRepository.save(medicalRecord)
      );
    }

    @Test
    void testSave_NullFirstName() {
      medicalRecord.setFirstName(null);

      assertThrows(IllegalArgumentException.class, () ->
        medicalRecordRepository.save(medicalRecord)
      );
    }

    @Test
    void testSave_NullLastName() {
      medicalRecord.setLastName(null);

      assertThrows(IllegalArgumentException.class, () ->
        medicalRecordRepository.save(medicalRecord)
      );
    }

    @Test
    void testSave_NullMedications() {
      medicalRecord.setMedications(null);

      List<MedicalRecord> medicalRecords = new ArrayList<>();
      when(jsonWrapper.getMedicalRecords()).thenReturn(medicalRecords);

      medicalRecordRepository.save(medicalRecord);

      verify(jsonWrapper, times(2)).getMedicalRecords();
      verify(dataPersistenceService).saveData();
      assertTrue(medicalRecords.contains(medicalRecord));
      assertTrue(medicalRecord.getMedications().isEmpty());
    }

    @Test
    void testSave_NullAllergies() {
      medicalRecord.setAllergies(null);

      List<MedicalRecord> medicalRecords = new ArrayList<>();
      when(jsonWrapper.getMedicalRecords()).thenReturn(medicalRecords);

      medicalRecordRepository.save(medicalRecord);

      verify(jsonWrapper, times(2)).getMedicalRecords();
      verify(dataPersistenceService).saveData();
      assertTrue(medicalRecords.contains(medicalRecord));
      assertTrue(medicalRecord.getAllergies().isEmpty());
    }
  }

  @Nested
  class UpdateMedicalRecord {

    @Test
    void testUpdate() {
      MedicalRecord updatedMedicalRecord = new MedicalRecord();
      updatedMedicalRecord.setFirstName("John");
      updatedMedicalRecord.setLastName("Doe");
      updatedMedicalRecord.setBirthdate("10-01-2000");

      List<MedicalRecord> medicalRecords = new ArrayList<>(
        Collections.singletonList(medicalRecord)
      );

      when(jsonWrapper.getMedicalRecords()).thenReturn(medicalRecords);

      medicalRecordRepository.update(updatedMedicalRecord);

      verify(jsonWrapper, times(1)).getMedicalRecords();
      verify(dataPersistenceService).saveData();

      System.out.println(medicalRecords);

      assertTrue(medicalRecords.get(0).getFirstName().equals("John"));
      assertTrue(medicalRecords.get(0).getLastName().equals("Doe"));
      assertTrue(medicalRecords.get(0).getBirthdate().equals("10-01-2000"));
      assertTrue(
        medicalRecords.get(0).getMedications().contains("medication1")
      );
      assertTrue(medicalRecords.get(0).getAllergies().contains("allergy1"));
    }

    @Test
    void testUpdate_NonExistingMedicalRecord() {
      List<MedicalRecord> medicalRecords = new ArrayList<>();
      when(jsonWrapper.getMedicalRecords()).thenReturn(medicalRecords);

      assertThrows(IllegalArgumentException.class, () ->
        medicalRecordRepository.update(medicalRecord)
      );
    }
  }

  @Nested
  class DeleteFireStation {

    @Test
    void testDelete() {
      List<MedicalRecord> medicalRecords = new ArrayList<>(
        Collections.singletonList(medicalRecord)
      );
      when(jsonWrapper.getMedicalRecords()).thenReturn(medicalRecords);

      medicalRecordRepository.delete(medicalRecord);

      verify(jsonWrapper, times(2)).getMedicalRecords();
      verify(dataPersistenceService, times(1)).saveData();
    }

    @Test
    void testDelete_NonExistingMedicalRecord() {
      List<MedicalRecord> medicalRecords = new ArrayList<>();
      when(jsonWrapper.getMedicalRecords()).thenReturn(medicalRecords);

      assertThrows(IllegalArgumentException.class, () ->
        medicalRecordRepository.delete(medicalRecord)
      );
    }
  }
}
