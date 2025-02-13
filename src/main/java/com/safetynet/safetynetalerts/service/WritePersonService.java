package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Person;

public interface WritePersonService {
  /**
   * Add a person to the database.
   * @param person
   */
  void addPerson(Person person);

  /**
   * Update a person in the database.
   * @param person
   */
  void updatePerson(Person person);

  /**
   * Delete a person from the database.
   * @param person
   */
  void deletePerson(Person person);
}
