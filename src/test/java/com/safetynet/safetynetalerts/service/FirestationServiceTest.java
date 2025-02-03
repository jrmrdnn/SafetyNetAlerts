package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class FirestationServiceTest {

    @Autowired
    private FireStationServiceInterface fireStationService;

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

    @Test
    public void testGetAddresses() {
        FireStation firestation1 = new FireStation();
        firestation1.setAddress("1509 Culver St");
        firestation1.setStation("1");

        FireStation firestation2 = new FireStation();
        firestation2.setAddress("29 15th St");
        firestation2.setStation("2");

        FireStation firestation3 = new FireStation();
        firestation3.setAddress("834 Binoc Ave");
        firestation3.setStation("1");

        JsonWrapper jsonWrapper = new JsonWrapper();
        jsonWrapper.setFireStations(Arrays.asList(firestation1, firestation2, firestation3));

        List<String> addresses = fireStationService.getAddresses("1");

        assertEquals(3, addresses.size());
        assertEquals("644 Gershwin Cir", addresses.get(0));
        assertEquals("908 73rd St", addresses.get(1));
    }

    @Test
    public void testGetAllFireStations() {
        FireStation firestation1 = new FireStation();
        firestation1.setAddress("1509 Culver St");
        firestation1.setStation("1");

        FireStation firestation2 = new FireStation();
        firestation2.setAddress("29 15th St");
        firestation2.setStation("2");

        JsonWrapper jsonWrapper = new JsonWrapper();
        jsonWrapper.setFireStations(Arrays.asList(firestation1, firestation2));

        List<FireStation> fireStations = fireStationService.getAllFireStations();

        assertEquals(13, fireStations.size());
        assertEquals("1509 Culver St", fireStations.get(0).getAddress());
        assertEquals("3", fireStations.get(0).getStation());
        assertEquals("29 15th St", fireStations.get(1).getAddress());
        assertEquals("2", fireStations.get(1).getStation());
    }
}
