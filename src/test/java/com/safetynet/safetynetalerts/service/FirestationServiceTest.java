package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FirestationServiceTest {

    @Autowired
    private FireStationService fireStationService;

    @Test
    public void testGetPersonsCoveredByStation() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("1509 Culver St");
        person1.setPhone("841-874-6512");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress("1509 Culver St");
        person2.setPhone("841-874-6513");

        FireStation firestation = new FireStation();
        firestation.setAddress("1509 Culver St");
        firestation.setStation("1");

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Doe");
        medicalRecord1.setBirthdate("03/06/1984");

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jane");
        medicalRecord2.setLastName("Doe");
        medicalRecord2.setBirthdate("03/06/2010");

        JsonWrapper jsonWrapper = new JsonWrapper();
        jsonWrapper.setPersons(Arrays.asList(person1, person2));
        jsonWrapper.setFireStations(Arrays.asList(firestation));
        jsonWrapper.setMedicalRecords(Arrays.asList(medicalRecord1, medicalRecord2));

        FireStationDTO ireStation = fireStationService.getPersonsCoveredByStation("1");

        assertEquals(6, ireStation.getPersons().size());
        assertEquals(1, ireStation.getAdultCount());
        assertEquals(5, ireStation.getChildCount());
    }
}
