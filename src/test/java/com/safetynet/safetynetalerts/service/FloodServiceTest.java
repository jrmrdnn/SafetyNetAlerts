package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
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

public class FloodServiceTest {

    @Mock
    private JsonWrapper jsonWrapper;

    @Mock
    private CalculateAgeServiceInterface calculateAgeService;

    @InjectMocks
    private FloodService floodService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHouseholdsByStations() {
        List<String> stationNumbers = Arrays.asList("1", "2");

        FireStation fireStation1 = new FireStation();
        fireStation1.setStation("1");
        fireStation1.setAddress("address1");

        FireStation fireStation2 = new FireStation();
        fireStation2.setStation("2");
        fireStation2.setAddress("address2");

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("address1");
        person1.setCity("city");
        person1.setZip("zip");
        person1.setPhone("phone");
        person1.setEmail("email");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress("address2");
        person2.setCity("city");
        person2.setZip("zip");
        person2.setPhone("phone");
        person2.setEmail("email");

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

        when(jsonWrapper.getFireStations()).thenReturn(Arrays.asList(fireStation1, fireStation2));
        when(jsonWrapper.getPersons()).thenReturn(Arrays.asList(person1, person2));
        when(jsonWrapper.getMedicalRecords()).thenReturn(Arrays.asList(medicalRecord1, medicalRecord2));
        when(calculateAgeService.calculate("01/01/1980")).thenReturn(41);
        when(calculateAgeService.calculate("02/02/1990")).thenReturn(31);

        List<HouseholdInfoDTO> result = floodService.getHouseholdsByStations(stationNumbers);

        assertEquals(2, result.size());

        HouseholdInfoDTO household1 = result.get(1);
        assertEquals("address1", household1.getAddress());
        assertEquals(1, household1.getPersons().size());

        PersonInfoDTO personInfo1 = household1.getPersons().get(0);
        assertEquals("John", personInfo1.getFirstName());
        assertEquals("Doe", personInfo1.getLastName());
        assertEquals("phone", personInfo1.getPhone());
        assertEquals(41, personInfo1.getAge());
        assertEquals(Arrays.asList("med1"), personInfo1.getMedications());
        assertEquals(Arrays.asList("allergy1"), personInfo1.getAllergies());

        HouseholdInfoDTO household2 = result.get(0);
        assertEquals("address2", household2.getAddress());
        assertEquals(1, household2.getPersons().size());

        PersonInfoDTO personInfo2 = household2.getPersons().get(0);
        assertEquals("Jane", personInfo2.getFirstName());
        assertEquals("Doe", personInfo2.getLastName());
        assertEquals("phone", personInfo2.getPhone());
        assertEquals(31, personInfo2.getAge());
        assertEquals(Arrays.asList("med2"), personInfo2.getMedications());
        assertEquals(Arrays.asList("allergy2"), personInfo2.getAllergies());
    }
}