package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.constants.JacksonConstants;
import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.dto.PersonDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.JsonWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService implements FireStationServiceInterface {
    @Autowired
    private JsonWrapper jsonWrapper;

    @Autowired
    private JacksonServiceInterface jacksonService;

    @Autowired
    private CalculateAgeServiceInterface calculateAgeService;

    @Override
    public List<FireStation> getAllFireStations() {
        return jsonWrapper.getFireStations();
    }

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
                if (calculateAgeService.isChild(age)) {
                    childCount++;
                } else {
                    adultCount++;
                }
            }
        }

        return getCoverage(coveredPersons, childCount, adultCount);
    }

    @Override
    public List<String> getAddresses(String stationNumber) {
        return jsonWrapper.getFireStations().stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber)).map(FireStation::getAddress)
                .collect(Collectors.toList());
    }

    @Override
    public FireStation addFireStation(FireStation firestation) {
        List<FireStation> allFireStations = jsonWrapper.getFireStations();
        Optional<FireStation> getFireStation = getExistingFireStation(firestation.getAddress(), allFireStations);

        if (getFireStation.isPresent()) {
            throw new IllegalArgumentException("Firestation already exists");
        } else {
            allFireStations.add(firestation);
            jsonWrapper.setFireStations(allFireStations);
            jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
            return firestation;
        }
    }

    @Override
    public FireStation updateFireStation(FireStation fireStation) {
        List<FireStation> allFireStations = jsonWrapper.getFireStations();
        Optional<FireStation> getFireStation = getExistingFireStation(fireStation.getAddress(), allFireStations);

        if (getFireStation.isPresent()) {
            setUpdateFirestation(fireStation, allFireStations, getFireStation);
            jsonWrapper.setFireStations(allFireStations);
            jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
            return fireStation;
        } else {
            throw new IllegalArgumentException("Firestation not found");
        }
    }

    @Override
    public void deleteFireStation(FireStation fireStation) {
        List<FireStation> allFireStations = jsonWrapper.getFireStations();
        Optional<FireStation> getFireStation = getExistingFireStation(fireStation.getAddress(), allFireStations);

        if (getFireStation.isPresent()) {
            allFireStations.remove(getFireStation.get());
            jsonWrapper.setFireStations(allFireStations);
            jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
        } else {
            throw new IllegalArgumentException("Firestation not found");
        }
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
            PersonDTO personInfo = new PersonDTO();
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

    /**
     * Get the existing fire station
     * 
     * @param address
     * @param firestations
     * @return
     */
    private Optional<FireStation> getExistingFireStation(String address, List<FireStation> firestations) {
        Optional<FireStation> existingFirestation = firestations.stream()
                .filter(firestation -> firestation.getAddress().equalsIgnoreCase(address)).findFirst();
        return existingFirestation;
    }

    /**
     * Set the updated fire station
     * 
     * @param fireStation
     * @param allFireStations
     * @param fireStationUpdate
     */
    private void setUpdateFirestation(FireStation fireStation, List<FireStation> allFireStations,
            Optional<FireStation> fireStationUpdate) {
        fireStationUpdate.stream().forEach(f -> {
            f.setStation(fireStation.getStation());
        });
        allFireStations.set(allFireStations.indexOf(fireStationUpdate.get()), fireStationUpdate.get());
    }

}