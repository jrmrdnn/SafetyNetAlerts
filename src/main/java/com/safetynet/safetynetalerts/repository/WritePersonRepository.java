package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;

public interface WritePersonRepository {
  /**
   * Save a person.
   * @param person
   */
  void save(Person person);

  /**
   * Update a person.
   * @param person
   */
  void update(Person person);

  /**
   * Delete a person.
   * @param person
   */
  void delete(Person person);
}
