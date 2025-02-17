package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.WriteMedicalRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService implements WriteMedicalRecordService {

  private final WriteMedicalRecordRepository writeMedicalRecordRepository;

  public MedicalRecordService(
    WriteMedicalRecordRepository writeMedicalRecordRepository
  ) {
    this.writeMedicalRecordRepository = writeMedicalRecordRepository;
  }

  @Override
  public void addMedicalRecord(MedicalRecord medicalRecord) {
    writeMedicalRecordRepository.save(medicalRecord);
  }

  @Override
  public void updateMedicalRecord(MedicalRecord medicalRecord) {
    writeMedicalRecordRepository.update(medicalRecord);
  }

  @Override
  public void deleteMedicalRecord(MedicalRecord medicalRecord) {
    writeMedicalRecordRepository.delete(medicalRecord);
  }
}
