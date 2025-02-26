package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

  private List<Person> persons;
  private List<FireStation> fireStations;
  private List<MedicalRecord> medicalRecords;

  @Mock
  private JacksonServiceInterface jacksonService;

  @Mock
  private ReadJson readJson;

  @InjectMocks
  private DataPersistenceService dataPersistenceService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    persons = List.of(new Person());
    fireStations = List.of(new FireStation());
    medicalRecords = List.of(new MedicalRecord());
  }

  @Test
  public void testSaveData() {
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

  @Test
  public void testSaveData_WhenReadJsonReturnsNull() {
    when(readJson.getPersons()).thenReturn(null);
    when(readJson.getFireStations()).thenReturn(null);
    when(readJson.getMedicalRecords()).thenReturn(null);

    dataPersistenceService.saveData();

    doThrow(NullPointerException.class)
      .when(jacksonService)
      .saveToFile(anyString(), any());

    assertThrows(NullPointerException.class, () ->
      dataPersistenceService.saveData()
    );
  }

  @Test
  public void testSaveData_WhenJacksonServiceThrowsException() {
    when(readJson.getPersons()).thenReturn(persons);
    when(readJson.getFireStations()).thenReturn(fireStations);
    when(readJson.getMedicalRecords()).thenReturn(medicalRecords);

    doThrow(RuntimeException.class)
      .when(jacksonService)
      .saveToFile(anyString(), any());

    assertThrows(RuntimeException.class, () -> dataPersistenceService.saveData()
    );
  }
}
