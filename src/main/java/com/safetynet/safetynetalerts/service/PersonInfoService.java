package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class PersonInfoService implements PersonInfoServiceInterface {

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private MedicalRecordServiceInterface medicalRecordService;

    @Autowired
    private CalculateAgeServiceInterface calculateAgeService;

    @Override
    public List<PersonInfoDTO> getPersonInfoByLastName(String lastName) {
        List<PersonInfoDTO> personInfoList = new ArrayList<>();
        List<Person> personsWithLastName = getPersonsWithLastName(lastName);

        for (Person person : personsWithLastName) {
            MedicalRecord medicalRecord = getMedicalRecord(person);

            if (medicalRecord != null) {
                getPersonInfo(personInfoList, person, medicalRecord);
            }
        }

        return personInfoList;
    }

    /**
     * Get a list of persons with the given address and their information
     * 
     * @param address the address of the persons
     * @return a list of PersonInfoDTO
     */
    private List<Person> getPersonsWithLastName(String lastName) {
        List<Person> personsWithLastName = personService.getAllPersons().stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());
        return personsWithLastName;
    }

    /**
     * Get the medical record of a person
     * 
     * @param person the person
     * @return the medical record
     */
    private MedicalRecord getMedicalRecord(Person person) {
        MedicalRecord medicalRecord = medicalRecordService.getAllMedicalRecords().stream().filter(
                m -> m.getFirstName().equals(person.getFirstName()) && m.getLastName().equals(person.getLastName()))
                .findFirst().orElse(null);
        return medicalRecord;
    }

    /**
     * Get the information of a person
     * 
     * @param personInfoList the list of person information
     * @param person         the person
     * @param medicalRecord  the medical record
     */
    private void getPersonInfo(List<PersonInfoDTO> personInfoList, Person person, MedicalRecord medicalRecord) {
        PersonInfoDTO personInfo = new PersonInfoDTO();
        personInfo.setFirstName(person.getFirstName());
        personInfo.setLastName(person.getLastName());
        personInfo.setAddress(person.getAddress());
        personInfo.setAge(calculateAgeService.calculate(medicalRecord.getBirthdate()));
        personInfo.setEmail(person.getEmail());
        personInfo.setMedications(medicalRecord.getMedications());
        personInfo.setAllergies(medicalRecord.getAllergies());
        personInfoList.add(personInfo);
    }
}
