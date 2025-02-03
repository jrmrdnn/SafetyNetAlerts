package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.JsonWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationService implements FireStationServiceInterface {

    @Autowired
    private JsonWrapper jsonWrapper;

    @Autowired
    private CalculateAgeServiceInterface calculateAgeService;

    /**
     * Get the list of all fire stations
     * 
     * @return list of all fire stations
     */
    @Override
    public List<FireStation> getAllFireStations() {
        return jsonWrapper.getFireStations();
    }

    /**
     * Get the list of persons covered by a fire station
     * 
     * @param stationNumber number of the fire station
     * @return list of persons covered by the fire station
     */
    @Override
    public FireStationDTO getPersonsCoveredByStation(String stationNumber) {
        List<String> addresses = getAddresses(stationNumber);
        List<Person> coveredPersons = getCoveredPersons(addresses);

        int childCount = 0;
        int adultCount = 0;

        for (Person person : coveredPersons) {
            MedicalRecord medicalRecord = getMedicalRecord(person);

            if (medicalRecord != null) {
                int age = calculateAgeService.calculate(medicalRecord.getBirthdate());
                if (calculateAgeService.isAdult(age)) {
                    childCount++;
                } else {
                    adultCount++;
                }
            }
        }

        return getCoverage(coveredPersons, childCount, adultCount);
    }

    /**
     * Get the list of addresses covered by a fire station
     * 
     * @param stationNumber number of the fire station
     * @return list of addresses covered by the fire station
     */
    @Override
    public List<String> getAddresses(String stationNumber) {
        return jsonWrapper.getFireStations().stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber)).map(FireStation::getAddress)
                .collect(Collectors.toList());
    }

    /**
     * Get the list of persons covered by a fire station
     * 
     * @param addresses
     * @return list of persons covered by the fire station
     */
    private List<Person> getCoveredPersons(List<String> addresses) {
        return jsonWrapper.getPersons().stream().filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.toList());
    }

    /**
     * Get the medical record of a person
     * 
     * @param person
     * @return medical record of the person
     */
    private MedicalRecord getMedicalRecord(Person person) {
        return jsonWrapper.getMedicalRecords().stream()
                .filter(record -> record.getFirstName().equals(person.getFirstName())
                        && record.getLastName().equals(person.getLastName()))
                .findFirst().orElse(null);
    }

    /**
     * Get the coverage of a fire station
     * 
     * @param coveredPersons list of persons covered by the fire station
     * @param childCount     number of children covered by the fire station
     * @param adultCount     number of adults covered by the fire station
     * @return coverage of the fire station
     */
    private FireStationDTO getCoverage(List<Person> coveredPersons, int childCount, int adultCount) {
        FireStationDTO coverage = new FireStationDTO();
        coverage.setPersons(coveredPersons.stream().map(person -> {
            PersonInfoDTO personInfo = new PersonInfoDTO();
            personInfo.setFirstName(person.getFirstName());
            personInfo.setLastName(person.getLastName());
            personInfo.setAddress(person.getAddress());
            personInfo.setPhone(person.getPhone());
            return personInfo;
        }).collect(Collectors.toList()));
        coverage.setAdultCount(adultCount);
        coverage.setChildCount(childCount);

        return coverage;
    }
}