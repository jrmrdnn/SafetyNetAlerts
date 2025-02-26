package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ReadPersonRepository {
  /**
   * Find persons by addresses.
   * @param addresses the addresses
   * @return the list
   */
  List<Person> findPersonsByAddresses(List<FireStation> addresses);

  /**
   * Find persons at address.
   * @param address the address
   * @return the list
   */
  public List<Person> findPersonsAtAddress(String address);

  /**
   * Find phone numbers by address.
   * @param addresses the addresses
   * @return the set
   */
  Set<String> findPhoneNumbersByAddress(List<FireStation> addresses);

  /**
   * Find and group persons by address.
   * @param addresses the addresses
   * @return the map
   */
  Map<String, List<Person>> findAndGroupPersonsByAddress(Set<String> addresses);

  /**
   * Find persons with last name.
   * @param lastName the last name
   * @return the list
   */
  List<Person> findPersonsWithLastName(String lastName);

  /**
   * Find emails by city.
   * @param city the city
   * @return the set
   */
  Set<String> findEmailsByCity(String city);

  /**
   * Find person by first name and last name.
   * @param person the person
   * @return the optional
   */
  Optional<Person> findPersonByFirstNameAndLastName(Person person);
}
