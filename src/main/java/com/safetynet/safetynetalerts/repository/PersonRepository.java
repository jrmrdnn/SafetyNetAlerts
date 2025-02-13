package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.DataPersistenceServiceInterface;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository
  implements ReadPersonRepository, WritePersonRepository {

  private final JsonWrapper jsonWrapper;
  private final DataPersistenceServiceInterface dataPersistenceService;

  public PersonRepository(
    JsonWrapper jsonWrapper,
    DataPersistenceServiceInterface dataPersistenceService
  ) {
    this.jsonWrapper = jsonWrapper;
    this.dataPersistenceService = dataPersistenceService;
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

  @Override
  public Map<String, List<Person>> findAndGroupPersonsByAddress(
    Set<String> addresses
  ) {
    return jsonWrapper
      .getPersons()
      .stream()
      .filter(p -> addresses.contains(p.getAddress()))
      .collect(Collectors.groupingBy(Person::getAddress));
  }

  @Override
  public List<Person> findPersonsWithLastName(String lastName) {
    return jsonWrapper
      .getPersons()
      .stream()
      .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
      .collect(Collectors.toList());
  }

  @Override
  public Set<String> findEmailsByCity(String city) {
    return jsonWrapper
      .getPersons()
      .stream()
      .filter(p -> p.getCity().equalsIgnoreCase(city))
      .map(Person::getEmail)
      .collect(Collectors.toSet());
  }

  @Override
  public Optional<Person> findPersonByFirstNameAndLastName(Person person) {
    return jsonWrapper
      .getPersons()
      .stream()
      .filter(
        p ->
          p.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
          p.getLastName().equalsIgnoreCase(person.getLastName())
      )
      .findFirst();
  }

  @Override
  public void save(Person person) {
    Optional<Person> findPerson = findPersonByFirstNameAndLastName(person);

    if (findPerson.isPresent()) {
      throw new IllegalArgumentException("Person already exists");
    }

    jsonWrapper.getPersons().add(person);
    dataPersistenceService.saveData();
  }

  @Override
  public void update(Person person) {
    Optional<Person> findPerson = findPersonByFirstNameAndLastName(person);

    if (findPerson.isEmpty()) {
      throw new IllegalArgumentException("Person not found");
    }

    jsonWrapper
      .getPersons()
      .replaceAll(p -> {
        if (p.equals(findPerson.get())) {
          Person updatedPerson = new Person();
          updatedPerson.setFirstName(
            person.getFirstName() != null
              ? person.getFirstName()
              : p.getFirstName()
          );
          updatedPerson.setLastName(
            person.getLastName() != null
              ? person.getLastName()
              : p.getLastName()
          );
          updatedPerson.setAddress(
            person.getAddress() != null ? person.getAddress() : p.getAddress()
          );
          updatedPerson.setCity(
            person.getCity() != null ? person.getCity() : p.getCity()
          );
          updatedPerson.setZip(
            person.getZip() != null ? person.getZip() : p.getZip()
          );
          updatedPerson.setPhone(
            person.getPhone() != null ? person.getPhone() : p.getPhone()
          );
          updatedPerson.setEmail(
            person.getEmail() != null ? person.getEmail() : p.getEmail()
          );
          return updatedPerson;
        }
        return p;
      });

    dataPersistenceService.saveData();
  }

  @Override
  public void delete(Person person) {
    Optional<Person> findPerson = findPersonByFirstNameAndLastName(person);

    if (findPerson.isEmpty()) {
      throw new IllegalArgumentException("Person not found");
    }

    jsonWrapper.getPersons().remove(findPerson.get());
    dataPersistenceService.saveData();
  }
}
