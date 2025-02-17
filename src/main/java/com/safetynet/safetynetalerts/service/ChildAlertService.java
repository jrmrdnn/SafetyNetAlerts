package com.safetynet.safetynetalerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.ChildAlertDTO.HouseholdMember;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildAlertService implements ChildAlertServiceInterface {

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private MedicalRecordServiceInterface medicalRecordService;

    @Autowired
    private CalculateAgeServiceInterface calculateAgeService;

    @Override
    public List<ChildAlertDTO> getChildrenAtAddress(String address) {
        List<Person> personsAtAddress = getPersonsAtAddress(address);
        List<ChildAlertDTO> children = new ArrayList<>();

        for (Person person : personsAtAddress) {
            MedicalRecord medicalRecord = getMedicalRecord(person);

            if (medicalRecord != null) {
                int age = calculateAgeService.calculate(medicalRecord.getBirthdate());

                if (calculateAgeService.isChild(age)) {
                    ChildAlertDTO child = getChild(person, age);
                    getHouseholdMembers(personsAtAddress, children, person, child);
                }
            }
        }
        return children;
    }

    /**
     * Get persons at address
     * 
     * @param address address
     * @return list of persons at address
     */
    private List<Person> getPersonsAtAddress(String address) {
        return personService.getAllPersons().stream().filter(p -> p.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    /**
     * Get medical record of person
     * 
     * @param person person
     * @return medical record
     */
    private MedicalRecord getMedicalRecord(Person person) {
        return medicalRecordService.getAllMedicalRecords().stream().filter(
                m -> m.getFirstName().equals(person.getFirstName()) && m.getLastName().equals(person.getLastName()))
                .findFirst().orElse(null);
    }

    /**
     * Get child information
     * 
     * @param person person
     * @param age    age
     * @return child information
     */
    private ChildAlertDTO getChild(Person person, int age) {
        ChildAlertDTO child = new ChildAlertDTO();
        child.setFirstName(person.getFirstName());
        child.setLastName(person.getLastName());
        child.setAge(age);
        return child;
    }

    /**
     * Get household members information
     * 
     * @param personsAtAddress persons at address
     * @param children         children
     * @param person           person
     * @param child            child
     */
    private void getHouseholdMembers(List<Person> personsAtAddress, List<ChildAlertDTO> children, Person person,
            ChildAlertDTO child) {
        List<HouseholdMember> householdMembers = personsAtAddress.stream().filter(
                p -> !(p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName())))
                .map(p -> {
                    HouseholdMember member = new ChildAlertDTO.HouseholdMember();
                    member.setFirstName(p.getFirstName());
                    member.setLastName(p.getLastName());
                    return member;
                }).collect(Collectors.toList());

        child.setHouseholdMembers(householdMembers);
        children.add(child);
    }
}
