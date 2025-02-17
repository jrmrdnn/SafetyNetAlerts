package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;

public interface WriteMedicalRecordService {
  /**
   * Add a medical record.
   * @param medicalRecord
   */
  void addMedicalRecord(MedicalRecord medicalRecord);

  /**
   * Update a medical record.
   * @param medicalRecord
   */
  void updateMedicalRecord(MedicalRecord medicalRecord);

  /**
   * Delete a medical record.
   * @param medicalRecord
   */
  void deleteMedicalRecord(MedicalRecord medicalRecord);
}
