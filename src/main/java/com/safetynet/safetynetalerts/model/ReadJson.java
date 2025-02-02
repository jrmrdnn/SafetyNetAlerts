package com.safetynet.safetynetalerts.model;

import java.util.List;

public interface ReadJson {
  /**
   * Get the list of persons.
   *
   * @return the list of persons
   */
  List<Person> getPersons();

  /**
   * Get the list of fire stations.
   *
   * @return the list of fire stations
   */
  List<FireStation> getFireStations();

  /**
   * Get the list of medical records.
   *
   * @return the list of medical records
   */
  List<MedicalRecord> getMedicalRecords();
}
