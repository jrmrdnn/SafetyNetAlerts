package com.safetynet.safetynetalerts.service;

import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.ReadMedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.WriteMedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MedicalRecordServiceTest {

  private MedicalRecordService medicalRecordService;

  @Mock
  private ReadMedicalRecordRepository readRepository;

  @Mock
  private WriteMedicalRecordRepository writeRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    medicalRecordService = new MedicalRecordService(writeRepository);
  }

  @Test
  void addMedicalRecord_ShouldCallSave() {
    MedicalRecord record = new MedicalRecord();
    medicalRecordService.addMedicalRecord(record);
    verify(writeRepository).save(record);
  }

  @Test
  void updateMedicalRecord_ShouldCallUpdate() {
    MedicalRecord record = new MedicalRecord();
    medicalRecordService.updateMedicalRecord(record);
    verify(writeRepository).update(record);
  }

  @Test
  void deleteMedicalRecord_ShouldCallDelete() {
    MedicalRecord record = new MedicalRecord();
    medicalRecordService.deleteMedicalRecord(record);
    verify(writeRepository).delete(record);
  }
}
