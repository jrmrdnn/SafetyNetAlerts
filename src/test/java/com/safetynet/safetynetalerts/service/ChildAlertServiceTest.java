package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
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

public class ChildAlertServiceTest {

    @InjectMocks
    private ChildAlertService childAlertService;

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
    public void testGetChildrenAtAddress_withChildren() {

        Person child1 = new Person();
        child1.setFirstName("John");
        child1.setLastName("Doe");
        child1.setAddress("123 Main St");
        child1.setCity("City");
        child1.setZip("12345");
        child1.setPhone("123-456-7890");
        child1.setEmail("john@example.com");

        Person child2 = new Person();
        child2.setFirstName("Jane");
        child2.setLastName("Doe");
        child2.setAddress("123 Main St");
        child2.setCity("City");
        child2.setZip("12345");
        child2.setPhone("123-456-7890");
        child2.setEmail("jane@example.com");

        Person adult = new Person();
        adult.setFirstName("Adult");
        adult.setLastName("Doe");
        adult.setAddress("123 Main St");
        adult.setCity("City");
        adult.setZip("12345");
        adult.setPhone("123-456-7890");
        adult.setEmail("adult@example.com");

        MedicalRecord medicalRecordChild1 = new MedicalRecord();
        medicalRecordChild1.setFirstName("John");
        medicalRecordChild1.setLastName("Doe");
        medicalRecordChild1.setBirthdate("01/01/2015");

        MedicalRecord medicalRecordChild2 = new MedicalRecord();
        medicalRecordChild2.setFirstName("Jane");
        medicalRecordChild2.setLastName("Doe");
        medicalRecordChild2.setBirthdate("01/01/2016");

        MedicalRecord medicalRecordAdult = new MedicalRecord();
        medicalRecordAdult.setFirstName("Adult");
        medicalRecordAdult.setLastName("Doe");
        medicalRecordAdult.setBirthdate("01/01/1980");

        when(personService.getAllPersons()).thenReturn(Arrays.asList(child1, child2, adult));

        when(medicalRecordService.getAllMedicalRecords())
                .thenReturn(Arrays.asList(medicalRecordChild1, medicalRecordChild2, medicalRecordAdult));

        when(calculateAgeService.calculate("01/01/2015")).thenReturn(8);
        when(calculateAgeService.calculate("01/01/2016")).thenReturn(7);
        when(calculateAgeService.calculate("01/01/1980")).thenReturn(43);

        when(calculateAgeService.isChild(8)).thenReturn(true);
        when(calculateAgeService.isChild(7)).thenReturn(true);
        when(calculateAgeService.isChild(43)).thenReturn(false);

        List<ChildAlertDTO> result = childAlertService.getChildrenAtAddress("123 Main St");

        assertEquals(2, result.size());

        ChildAlertDTO childAlert1 = result.get(0);
        assertEquals("John", childAlert1.getFirstName());
        assertEquals("Doe", childAlert1.getLastName());
        assertEquals(8, childAlert1.getAge());
        assertEquals(2, childAlert1.getHouseholdMembers().size());

        ChildAlertDTO childAlert2 = result.get(1);
        assertEquals("Jane", childAlert2.getFirstName());
        assertEquals("Doe", childAlert2.getLastName());
        assertEquals(7, childAlert2.getAge());
        assertEquals(2, childAlert2.getHouseholdMembers().size());
    }

    @Test
    public void testGetChildrenAtAddress_noChildren() {
        String address = "123 Main St";

        Person adult = new Person();
        adult.setFirstName("Adult");
        adult.setLastName("Doe");
        adult.setAddress(address);
        adult.setCity("City");
        adult.setZip("12345");
        adult.setPhone("123-456-7890");
        adult.setEmail("adult@example.com");

        MedicalRecord medicalRecordAdult = new MedicalRecord();
        medicalRecordAdult.setFirstName("Adult");
        medicalRecordAdult.setLastName("Doe");
        medicalRecordAdult.setBirthdate("01/01/1980");

        when(personService.getAllPersons()).thenReturn(Collections.singletonList(adult));

        when(medicalRecordService.getAllMedicalRecords()).thenReturn(Collections.singletonList(medicalRecordAdult));

        when(calculateAgeService.calculate("01/01/1980")).thenReturn(43);

        when(calculateAgeService.isChild(43)).thenReturn(false);

        List<ChildAlertDTO> result = childAlertService.getChildrenAtAddress(address);

        assertEquals(0, result.size());
    }
}