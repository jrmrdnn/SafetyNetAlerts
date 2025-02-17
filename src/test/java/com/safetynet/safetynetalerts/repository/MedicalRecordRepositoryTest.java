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

    verify(jsonWrapper, times(2)).getMedicalRecords();
    verify(dataPersistenceService).saveData();

    assertFalse(medicalRecords.contains(medicalRecord));
  }

  @Test
  void testUpdate_NonExistingMedicalRecord() {
    List<MedicalRecord> medicalRecords = new ArrayList<>();
    when(jsonWrapper.getMedicalRecords()).thenReturn(medicalRecords);

    assertThrows(IllegalArgumentException.class, () ->
      medicalRecordRepository.update(medicalRecord)
    );
  }

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
