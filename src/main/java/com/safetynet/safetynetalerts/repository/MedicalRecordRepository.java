package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.DataPersistenceServiceInterface;
import com.safetynet.safetynetalerts.util.EntityUpdater;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MedicalRecordRepository
  implements ReadMedicalRecordRepository, WriteMedicalRecordRepository {

  private final JsonWrapper jsonWrapper;
  private final DataPersistenceServiceInterface dataPersistenceService;

  public MedicalRecordRepository(
    JsonWrapper jsonWrapper,
    DataPersistenceServiceInterface dataPersistenceService
  ) {
    this.jsonWrapper = jsonWrapper;
    this.dataPersistenceService = dataPersistenceService;
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

  @Override
  public Optional<MedicalRecord> findByFirstNameAndLastName(
    MedicalRecord medicalRecord
  ) {
    return jsonWrapper
      .getMedicalRecords()
      .stream()
      .filter(
        m ->
          m.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()) &&
          m.getLastName().equalsIgnoreCase(medicalRecord.getLastName())
      )
      .findFirst();
  }

  @Override
  public void save(MedicalRecord medicalRecord) {
    if (
      medicalRecord.getFirstName() == null ||
      medicalRecord.getLastName() == null
    ) {
      throw new IllegalArgumentException(
        "First name and last name are required"
      );
    }

    Optional<MedicalRecord> findMedicalRecord = findByFirstNameAndLastName(
      medicalRecord
    );

    if (findMedicalRecord.isPresent()) {
      throw new IllegalArgumentException("Medical record already exists");
    }

    if (medicalRecord.getMedications() == null) {
      medicalRecord.setMedications(List.of());
    }

    if (medicalRecord.getAllergies() == null) {
      medicalRecord.setAllergies(List.of());
    }

    jsonWrapper.getMedicalRecords().add(medicalRecord);
    dataPersistenceService.saveData();
  }

  @Override
  public void update(MedicalRecord medicalRecord) {
    Optional<MedicalRecord> findMedicalRecord = findByFirstNameAndLastName(
      medicalRecord
    );

    if (findMedicalRecord.isEmpty()) {
      throw new IllegalArgumentException("Medical record not found");
    }

    EntityUpdater.updateFields(findMedicalRecord.get(), medicalRecord);
    dataPersistenceService.saveData();
  }

  @Override
  public void delete(MedicalRecord medicalRecord) {
    Optional<MedicalRecord> findMedicalRecord = findByFirstNameAndLastName(
      medicalRecord
    );

    if (findMedicalRecord.isEmpty()) {
      throw new IllegalArgumentException("Medical record not found");
    }

    jsonWrapper.getMedicalRecords().remove(findMedicalRecord.get());
    dataPersistenceService.saveData();
  }
}
