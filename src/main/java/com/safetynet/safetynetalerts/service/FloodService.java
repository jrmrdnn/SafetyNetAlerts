package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloodService implements FloodServiceInterface {

    @Autowired
    private JsonWrapper jsonWrapper;

    @Autowired
    private CalculateAgeServiceInterface calculateAgeService;

    @Override
    public List<HouseholdInfoDTO> getHouseholdsByStations(List<String> stationNumbers) {
        Set<String> addresses = getAddresses(stationNumbers);
        Map<String, List<Person>> households = getHouseholds(addresses);
        return getResult(households);
    }

    /**
     * Get the list of persons covered by a fire station
     * 
     * @param entry entry of the map of households
     * @return list of persons covered by the fire station
     */
    private List<PersonInfoDTO> getPersonInfos(Map.Entry<String, List<Person>> entry) {
        return entry.getValue().stream().map(person -> {
            PersonInfoDTO personInfo = new PersonInfoDTO();
            personInfo.setFirstName(person.getFirstName());
            personInfo.setLastName(person.getLastName());
            personInfo.setPhone(person.getPhone());

            MedicalRecord medicalRecord = findMedicalRecord(person.getFirstName(), person.getLastName());

            if (medicalRecord != null) {
                personInfo.setAge(calculateAgeService.calculate(medicalRecord.getBirthdate()));
                personInfo.setMedications(medicalRecord.getMedications());
                personInfo.setAllergies(medicalRecord.getAllergies());
            }

            return personInfo;
        }).collect(Collectors.toList());
    }

    /**
     * Get the households covered by a list of addresses
     * 
     * @param addresses list of addresses
     * @return households covered by the addresses
     */
    private Map<String, List<Person>> getHouseholds(Set<String> addresses) {
        Map<String, List<Person>> households = jsonWrapper.getPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.groupingBy(Person::getAddress));
        return households;
    }

    /**
     * Get the addresses covered by a list of fire stations
     * 
     * @param stationNumbers list of fire station numbers
     * @return addresses covered by the fire stations
     */
    private Set<String> getAddresses(List<String> stationNumbers) {
        return jsonWrapper.getFireStations().stream()
                .filter(firestation -> stationNumbers.contains(firestation.getStation())).map(FireStation::getAddress)
                .collect(Collectors.toSet());
    }

    /**
     * Find a medical record by first name and last name
     * 
     * @param firstName first name of the person
     * @param lastName  last name of the person
     * @return medical record of the person
     */
    private MedicalRecord findMedicalRecord(String firstName, String lastName) {
        return jsonWrapper.getMedicalRecords().stream()
                .filter(record -> record.getFirstName().equals(firstName) && record.getLastName().equals(lastName))
                .findFirst().orElse(null);
    }

    /**
     * Get the result of the households
     * 
     * @param households map of households
     * @return list of households
     */
    private List<HouseholdInfoDTO> getResult(Map<String, List<Person>> households) {
        List<HouseholdInfoDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<Person>> entry : households.entrySet()) {
            List<PersonInfoDTO> personInfos = getPersonInfos(entry);

            HouseholdInfoDTO householdInfo = new HouseholdInfoDTO();
            householdInfo.setAddress(entry.getKey());
            householdInfo.setPersons(personInfos);

            result.add(householdInfo);
        }
        return result;
    }
}
