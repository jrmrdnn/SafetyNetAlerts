package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireDTO.PersonDetails;
import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.ReadFireStationRepository;
import com.safetynet.safetynetalerts.repository.ReadMedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.ReadPersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FireStationService implements ReadFireStationService {

  private final ReadPersonRepository readPersonRepository;
  private final ReadFireStationRepository readFireStationRepository;
  private final ReadMedicalRecordRepository readMedicalRecordRepository;
  private final CalculateAgeServiceInterface calculateAgeService;

  @Override
  public FireStationDTO getPersonsCoveredByStation(String stationNumber) {
    List<FireStation> findByAddress =
      readFireStationRepository.findByStationNumberToList(stationNumber);

    List<Person> findPersons = readPersonRepository.findPersonsByAddresses(
      findByAddress
    );

    int childCount = 0;
    int adultCount = 0;

    for (Person person : findPersons) {
      Optional<MedicalRecord> medicalRecord =
        readMedicalRecordRepository.findByFirstNameAndLastName(person);
      if (medicalRecord.isPresent()) {
        int age = calculateAgeService.calculate(
          medicalRecord.get().getBirthdate()
        );
        if (calculateAgeService.isChild(age)) {
          childCount++;
        } else {
          adultCount++;
        }
      }
    }

    return FireStationDTO.createFireStationDTO(
      findPersons,
      childCount,
      adultCount
    );
  }

  @Override
  public Set<String> getPhoneNumbersByFireStation(String stationNumber) {
    List<FireStation> findByAddress =
      readFireStationRepository.findByStationNumberToList(stationNumber);
    return readPersonRepository.findPhoneNumbersByAddress(findByAddress);
  }

  @Override
  public FireDTO getFireInfoByAddress(String address) {
    FireDTO fire = new FireDTO();
    List<PersonDetails> personDetailsList = new ArrayList<>();

    Optional<FireStation> stationNumber =
      readFireStationRepository.findByStationAddress(address);

    stationNumber.ifPresent(station ->
      fire.setStationNumber(station.getStation())
    );

    List<Person> personsAtAddress = readPersonRepository.findPersonsAtAddress(
      address
    );

    for (Person person : personsAtAddress) {
      Optional<MedicalRecord> medicalRecord =
        readMedicalRecordRepository.findByFirstNameAndLastName(person);
      if (medicalRecord.isPresent()) {
        int age = calculateAgeService.calculate(
          medicalRecord.get().getBirthdate()
        );
        personDetailsList.add(
          FireDTO.createPersonDetails(person, age, medicalRecord.get())
        );
      }
    }

    fire.setPersons(personDetailsList);

    return fire;
  }
}
