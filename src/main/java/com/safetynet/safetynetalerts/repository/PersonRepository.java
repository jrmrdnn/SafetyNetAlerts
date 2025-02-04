package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository implements ReadPersonRepository {

  private final JsonWrapper jsonWrapper;

  public PersonRepository(JsonWrapper jsonWrapper) {
    this.jsonWrapper = jsonWrapper;
  }

  @Override
  public List<Person> findPersonsByAddresses(List<FireStation> addresses) {
    List<String> addressList = addresses
      .stream()
      .map(FireStation::getAddress)
      .collect(Collectors.toList());

    return jsonWrapper
      .getPersons()
      .stream()
      .filter(person -> addressList.contains(person.getAddress()))
      .collect(Collectors.toList());
  }
}
