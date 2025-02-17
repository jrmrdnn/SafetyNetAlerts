package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;

public interface WriteMedicalRecordRepository {
  /**
   * Save a medical record.
   * @param medicalRecord
   */
  void save(MedicalRecord medicalRecord);

  /**
   * Update a medical record.
   * @param medicalRecord
   */
  void update(MedicalRecord medicalRecord);

  /**
   * Delete a medical record.
   * @param medicalRecord
   */
  void delete(MedicalRecord medicalRecord);
}
