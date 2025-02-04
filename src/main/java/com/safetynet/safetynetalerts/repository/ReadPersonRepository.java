package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;
import java.util.List;

public interface ReadPersonRepository {
  /**
   * Find persons by addresses.
   * @param addresses the addresses
   * @return the list
   */
  List<Person> findPersonsByAddresses(List<FireStation> addresses);
}
