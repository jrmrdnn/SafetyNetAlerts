package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireDTO.PersonDetails;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

public class FireServiceTest {

    @InjectMocks
    private FireService fireService;

    @Mock
    private PersonServiceInterface personService;

    @Mock
    private MedicalRecordServiceInterface medicalRecordService;

    @Mock
    private FireStationServiceInterface fireStationService;

    @Mock
    private CalculateAgeServiceInterface calculateAgeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFireInfoByAddress() {
        String address = "123 Test St";
        String stationNumber = "1";

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress(address);
        person1.setPhone("123-456-7890");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress(address);
        person2.setPhone("098-765-4321");

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Doe");
        medicalRecord1.setBirthdate("01/01/1980");
        medicalRecord1.setMedications(Arrays.asList("med1"));
        medicalRecord1.setAllergies(Arrays.asList("allergy1"));

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jane");
        medicalRecord2.setLastName("Doe");
        medicalRecord2.setBirthdate("02/02/1990");
        medicalRecord2.setMedications(Arrays.asList("med2"));
        medicalRecord2.setAllergies(Arrays.asList("allergy2"));

        FireStation fireStation = new FireStation();
        fireStation.setAddress(address);
        fireStation.setStation(stationNumber);

        when(fireStationService.getAllFireStations()).thenReturn(Arrays.asList(fireStation));
        when(personService.getAllPersons()).thenReturn(Arrays.asList(person1, person2));
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(Arrays.asList(medicalRecord1, medicalRecord2));
        when(calculateAgeService.calculate("01/01/1980")).thenReturn(41);
        when(calculateAgeService.calculate("02/02/1990")).thenReturn(31);

        FireDTO fireDTO = fireService.getFireInfoByAddress(address);

        assertEquals(stationNumber, fireDTO.getStationNumber());
        List<PersonDetails> persons = fireDTO.getPersons();
        assertEquals(2, persons.size());

        PersonDetails personDetails1 = persons.get(0);
        assertEquals("John", personDetails1.getFirstName());
        assertEquals("Doe", personDetails1.getLastName());
        assertEquals("123-456-7890", personDetails1.getPhone());
        assertEquals(41, personDetails1.getAge());
        assertEquals(Arrays.asList("med1"), personDetails1.getMedications());
        assertEquals(Arrays.asList("allergy1"), personDetails1.getAllergies());

        PersonDetails personDetails2 = persons.get(1);
        assertEquals("Jane", personDetails2.getFirstName());
        assertEquals("Doe", personDetails2.getLastName());
        assertEquals("098-765-4321", personDetails2.getPhone());
        assertEquals(31, personDetails2.getAge());
        assertEquals(Arrays.asList("med2"), personDetails2.getMedications());
        assertEquals(Arrays.asList("allergy2"), personDetails2.getAllergies());
    }
}