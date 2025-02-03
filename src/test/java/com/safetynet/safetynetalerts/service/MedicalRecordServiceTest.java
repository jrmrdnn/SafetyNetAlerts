package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

public class MedicalRecordServiceTest {

    @Mock
    private JsonWrapper jsonWrapper;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMedicalRecords() {
        MedicalRecord record1 = new MedicalRecord();
        record1.setFirstName("John");
        record1.setLastName("Doe");
        record1.setBirthdate("01/01/1980");
        record1.setMedications(Arrays.asList("med1"));
        record1.setAllergies(Arrays.asList("allergy1"));

        MedicalRecord record2 = new MedicalRecord();
        record2.setFirstName("Jane");
        record2.setLastName("Doe");
        record2.setBirthdate("02/02/1990");
        record2.setMedications(Arrays.asList("med2"));
        record2.setAllergies(Arrays.asList("allergy2"));

        List<MedicalRecord> expectedRecords = Arrays.asList(record1, record2);

        when(jsonWrapper.getMedicalRecords()).thenReturn(expectedRecords);

        List<MedicalRecord> actualRecords = medicalRecordService.getAllMedicalRecords();

        assertEquals(expectedRecords, actualRecords);
    }
}