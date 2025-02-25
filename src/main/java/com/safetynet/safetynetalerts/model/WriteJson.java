package com.safetynet.safetynetalerts.model;

import java.util.List;

public interface WriteJson {
  /**
   * Set the list of persons.
   *
   * @param persons the list of persons
   */
  void setPersons(List<Person> persons);

  /**
   * Set the list of fire stations.
   *
   * @param fireStations the list of fire stations
   */
  void setFireStations(List<FireStation> fireStations);

  /**
   * Set the list of medical records.
   *
   * @param medicalRecords the list of medical records
   */
  void setMedicalRecords(List<MedicalRecord> medicalRecords);
}
