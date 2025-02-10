package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireDTO.PersonDetails;
import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.ReadFireStationRepository;
import com.safetynet.safetynetalerts.repository.ReadMedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.ReadPersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

  @Override
  public List<HouseholdInfoDTO> getHouseholdsByStations(
    List<String> stationNumbers
  ) {
    Set<String> addresses =
      readFireStationRepository.findAllStationsNumberToSet(stationNumbers);
    Map<String, List<Person>> groupPersonsByAddress =
      readPersonRepository.findAndGroupPersonsByAddress(addresses);
    return createHouseholdInfoDTO(groupPersonsByAddress);
  }

  /**
   * Get the result of the households
   *
   * @param households map of households
   * @return list of households
   */
  private List<HouseholdInfoDTO> createHouseholdInfoDTO(
    Map<String, List<Person>> households
  ) {
    List<HouseholdInfoDTO> listHouseholdInfo = new ArrayList<>();

    for (Map.Entry<String, List<Person>> entry : households.entrySet()) {
      List<PersonInfoDTO> personInfos = createPersonInfoDTO(entry);
      HouseholdInfoDTO householdInfo = new HouseholdInfoDTO();
      householdInfo.setAddress(entry.getKey());
      householdInfo.setPersons(personInfos);
      listHouseholdInfo.add(householdInfo);
    }

    return listHouseholdInfo;
  }

  /**
   * Get the list of persons covered by a fire station
   *
   * @param entry entry of the map of households
   * @return list of persons covered by the fire station
   */
  private List<PersonInfoDTO> createPersonInfoDTO(
    Map.Entry<String, List<Person>> entry
  ) {
    return entry
      .getValue()
      .stream()
      .map(p -> {
        PersonInfoDTO personInfo = new PersonInfoDTO();
        personInfo.setFirstName(p.getFirstName());
        personInfo.setLastName(p.getLastName());
        personInfo.setPhone(p.getPhone());

        Optional<MedicalRecord> medicalRecord =
          readMedicalRecordRepository.findByFirstNameAndLastName(p);
        if (medicalRecord.isPresent()) {
          MedicalRecord record = medicalRecord.get();
          personInfo.setAge(
            calculateAgeService.calculate(record.getBirthdate())
          );
          personInfo.setMedications(record.getMedications());
          personInfo.setAllergies(record.getAllergies());
        }

        return personInfo;
      })
      .collect(Collectors.toList());
  }
}
