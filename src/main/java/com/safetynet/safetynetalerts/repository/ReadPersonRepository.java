package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;
import java.util.List;
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
}
