package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireDTO.PersonDetails;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireService implements FireServiceInterface {

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private MedicalRecordServiceInterface medicalRecordService;

    @Autowired
    private FireStationServiceInterface fireStationService;

    @Autowired
    private CalculateAgeServiceInterface calculateAgeService;

    /**
     * Ge list of persons covered by a fire station
     * 
     * @param address the address
     * @return a list of persons
     */
    @Override
    public FireDTO getFireInfoByAddress(String address) {
        FireDTO fire = new FireDTO();
        List<PersonDetails> personDetailsList = new ArrayList<>();

        Optional<String> stationNumber = getStationNumber(address);
        stationNumber.ifPresent(fire::setStationNumber);

        List<Person> personsAtAddress = getPersonsAtAddress(address);

        for (Person person : personsAtAddress) {
            MedicalRecord medicalRecord = getMedicalRecord(person);
            if (medicalRecord != null) {
                addPersonDetails(personDetailsList, person, medicalRecord);
            }
        }

        fire.setPersons(personDetailsList);

        return fire;
    }

    /**
     * Get the station number of a fire station by address
     * 
     * @param address the address
     * @return the station number
     */
    private Optional<String> getStationNumber(String address) {
        return fireStationService.getAllFireStations().stream()
                .filter(fireStation -> fireStation.getAddress().equals(address)).map(FireStation::getStation)
                .findFirst();
    }

    /**
     * Get list of persons at an address
     * 
     * @param address the address
     * @return a list of persons
     */
    private List<Person> getPersonsAtAddress(String address) {
        List<Person> personsAtAddress = personService.getAllPersons().stream()
                .filter(persons -> persons.getAddress().equals(address)).collect(Collectors.toList());
        return personsAtAddress;
    }

    /**
     * Get the medical record of a person
     * 
     * @param person the person
     * @return the medical record
     */
    private MedicalRecord getMedicalRecord(Person person) {
        return medicalRecordService.getAllMedicalRecords().stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(person.getFirstName())
                        && medicalRecord.getLastName().equals(person.getLastName()))
                .findFirst().orElse(null);
    }

    /**
     * Add person details to a list
     * 
     * @param personDetailsList the list of person details
     * @param person            the person
     * @param medicalRecord     the medical record
     */
    private void addPersonDetails(List<PersonDetails> personDetailsList, Person person, MedicalRecord medicalRecord) {
        PersonDetails personDetails = new PersonDetails();
        personDetails.setFirstName(person.getFirstName());
        personDetails.setLastName(person.getLastName());
        personDetails.setPhone(person.getPhone());
        personDetails.setAge(calculateAgeService.calculate(medicalRecord.getBirthdate()));
        personDetails.setMedications(medicalRecord.getMedications());
        personDetails.setAllergies(medicalRecord.getAllergies());

        personDetailsList.add(personDetails);
    }
}
