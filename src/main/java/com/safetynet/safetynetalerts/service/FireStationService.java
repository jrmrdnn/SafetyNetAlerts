package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.ReadFireStationRepository;
import com.safetynet.safetynetalerts.repository.ReadMedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.ReadPersonRepository;
import java.util.List;
import java.util.Optional;
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
}
