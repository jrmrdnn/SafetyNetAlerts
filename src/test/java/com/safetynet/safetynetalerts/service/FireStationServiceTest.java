package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.ReadFireStationRepository;
import com.safetynet.safetynetalerts.repository.ReadMedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.ReadPersonRepository;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FireStationServiceTest {

  @Mock
  private ReadPersonRepository readPersonRepository;

  @Mock
  private ReadFireStationRepository readFireStationRepository;

  @Mock
  private ReadMedicalRecordRepository readMedicalRecordRepository;

  @Mock
  private CalculateAgeServiceInterface calculateAgeService;

  @InjectMocks
  private FireStationService fireStationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetPersonsCoveredByStation() {
    List<FireStation> fireStations = Arrays.asList(new FireStation());

    List<Person> persons = Arrays.asList(new Person(), new Person());

    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2000");

    when(readFireStationRepository.findByStationNumberToList("1")).thenReturn(
      fireStations
    );
    when(readPersonRepository.findPersonsByAddresses(fireStations)).thenReturn(
      persons
    );
    when(
      readMedicalRecordRepository.findByFirstNameAndLastName(any(Person.class))
    ).thenReturn(Optional.of(medicalRecord));
    when(calculateAgeService.calculate(anyString())).thenReturn(21);
    when(calculateAgeService.isChild(21)).thenReturn(false);

    FireStationDTO result = fireStationService.getPersonsCoveredByStation("1");

    assertEquals(2, result.getAdultCount());
    assertEquals(0, result.getChildCount());

    verify(readFireStationRepository, times(1)).findByStationNumberToList("1");
    verify(readPersonRepository, times(1)).findPersonsByAddresses(fireStations);
  }

  @Test
  void testGetPhoneNumbersByFirestation() {
    String stationNumber = "1";
    List<FireStation> fireStations = Arrays.asList(
      new FireStation(),
      new FireStation()
    );
    Set<String> phoneNumbers = new HashSet<>(Arrays.asList("123", "456"));

    when(
      readFireStationRepository.findByStationNumberToList(stationNumber)
    ).thenReturn(fireStations);
    when(
      readPersonRepository.findPhoneNumbersByAddress(fireStations)
    ).thenReturn(phoneNumbers);

    Set<String> result = fireStationService.getPhoneNumbersByFireStation(
      stationNumber
    );

    assertEquals(2, result.size());
    verify(readFireStationRepository, times(1)).findByStationNumberToList(
      stationNumber
    );
    verify(readPersonRepository, times(1)).findPhoneNumbersByAddress(
      fireStations
    );
  }

  @Test
  void testGetFireInfoByAddress() {
    FireStation fireStation = new FireStation();
    fireStation.setStation("1");
    List<Person> persons = Arrays.asList(new Person(), new Person());
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2000");

    when(readFireStationRepository.findByStationAddress("address")).thenReturn(
      Optional.of(fireStation)
    );
    when(readPersonRepository.findPersonsAtAddress("address")).thenReturn(
      persons
    );
    when(
      readMedicalRecordRepository.findByFirstNameAndLastName(any(Person.class))
    ).thenReturn(Optional.of(medicalRecord));
    when(calculateAgeService.calculate(anyString())).thenReturn(21);

    FireDTO result = fireStationService.getFireInfoByAddress("address");

    assertEquals("1", result.getStationNumber());
    assertEquals(2, result.getPersons().size());
    verify(readFireStationRepository, times(1)).findByStationAddress("address");
    verify(readPersonRepository, times(1)).findPersonsAtAddress("address");
  }
}
