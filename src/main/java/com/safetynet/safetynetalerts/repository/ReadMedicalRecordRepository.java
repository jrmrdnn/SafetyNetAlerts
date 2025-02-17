package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import java.util.Optional;

public interface ReadMedicalRecordRepository {
  /**
   * Find a medical record by first name and last name.
   * @param person
   * @return
   */
  Optional<MedicalRecord> findByFirstNameAndLastName(Person person);

  /**
   * Find a medical record by first name and last name.
   * @param medicalRecord
   * @return
   */
  Optional<MedicalRecord> findByFirstNameAndLastName(
    MedicalRecord medicalRecord
  );
}
