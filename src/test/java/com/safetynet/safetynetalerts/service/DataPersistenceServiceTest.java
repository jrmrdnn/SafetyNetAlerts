package com.safetynet.safetynetalerts.service;

import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.constants.JacksonConstants;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.ReadJson;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DataPersistenceServiceTest {

  @Mock
  private JacksonServiceInterface jacksonService;

  @Mock
  private ReadJson readJson;

  @InjectMocks
  private DataPersistenceService dataPersistenceService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testSaveData() {
    List<Person> persons = List.of(new Person());
    List<FireStation> fireStations = List.of(new FireStation());
    List<MedicalRecord> medicalRecords = List.of(new MedicalRecord());

    when(readJson.getPersons()).thenReturn(persons);
    when(readJson.getFireStations()).thenReturn(fireStations);
    when(readJson.getMedicalRecords()).thenReturn(medicalRecords);

    dataPersistenceService.saveData();

    JsonWrapper expectedData = new JsonWrapper();
    expectedData.setPersons(persons);
    expectedData.setFireStations(fireStations);
    expectedData.setMedicalRecords(medicalRecords);

    verify(jacksonService, times(1)).saveToFile(
      JacksonConstants.FILE_PATH,
      expectedData
    );
  }
}
