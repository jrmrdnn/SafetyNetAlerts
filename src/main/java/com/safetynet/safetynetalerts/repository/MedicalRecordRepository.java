package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MedicalRecordRepository implements ReadMedicalRecordRepository {

  private final JsonWrapper jsonWrapper;

  public MedicalRecordRepository(JsonWrapper jsonWrapper) {
    this.jsonWrapper = jsonWrapper;
  }

  @Override
  public Optional<MedicalRecord> findByFirstNameAndLastName(Person person) {
    return jsonWrapper
      .getMedicalRecords()
      .stream()
      .filter(
        m ->
          m.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
          m.getLastName().equalsIgnoreCase(person.getLastName())
      )
      .findFirst();
  }
}
