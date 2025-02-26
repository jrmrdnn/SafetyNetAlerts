package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  @Override
  public List<Person> findPersonsAtAddress(String address) {
    return jsonWrapper
      .getPersons()
      .stream()
      .filter(p -> p.getAddress().equalsIgnoreCase(address))
      .collect(Collectors.toList());
  }

  @Override
  public Set<String> findPhoneNumbersByAddress(List<FireStation> addresses) {
    Set<String> phoneNumbers = new HashSet<>();

    for (FireStation fireStation : addresses) {
      String address = fireStation.getAddress();
      Set<String> phones = jsonWrapper
        .getPersons()
        .stream()
        .filter(p -> p.getAddress().equals(address))
        .map(Person::getPhone)
        .collect(Collectors.toSet());
      phoneNumbers.addAll(phones);
    }

    return phoneNumbers;
  }
}
