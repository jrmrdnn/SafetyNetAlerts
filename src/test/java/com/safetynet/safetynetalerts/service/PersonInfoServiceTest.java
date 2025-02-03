package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
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
import java.util.Collections;
import java.util.List;

public class PersonInfoServiceTest {

    @InjectMocks
    private PersonInfoService personInfoService;

    @Mock
    private PersonServiceInterface personService;

    @Mock
    private MedicalRecordServiceInterface medicalRecordService;

    @Mock
    private CalculateAgeServiceInterface calculateAgeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPersonInfoByLastName() {
        String lastName = "Doe";
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setEmail("john.doe@example.com");

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("01/01/1980");
        medicalRecord.setMedications(Arrays.asList("med1", "med2"));
        medicalRecord.setAllergies(Arrays.asList("allergy1"));

        when(personService.getAllPersons()).thenReturn(Collections.singletonList(person));
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(Collections.singletonList(medicalRecord));
        when(calculateAgeService.calculate("01/01/1980")).thenReturn(42);

        List<PersonInfoDTO> result = personInfoService.getPersonInfoByLastName(lastName);

        assertEquals(1, result.size());
        PersonInfoDTO personInfo = result.get(0);
        assertEquals("John", personInfo.getFirstName());
        assertEquals("Doe", personInfo.getLastName());
        assertEquals("123 Main St", personInfo.getAddress());
        assertEquals(42, personInfo.getAge());
        assertEquals("john.doe@example.com", personInfo.getEmail());
        assertEquals(Arrays.asList("med1", "med2"), personInfo.getMedications());
        assertEquals(Arrays.asList("allergy1"), personInfo.getAllergies());
    }

    @Test
    public void testGetPersonInfoByLastName_NoMedicalRecord() {
        String lastName = "Doe";
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setEmail("john.doe@example.com");

        when(personService.getAllPersons()).thenReturn(Collections.singletonList(person));
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(Collections.emptyList());

        List<PersonInfoDTO> result = personInfoService.getPersonInfoByLastName(lastName);

        assertEquals(0, result.size());
    }
}