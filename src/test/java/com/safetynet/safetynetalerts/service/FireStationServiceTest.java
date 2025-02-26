package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.ReadFireStationRepository;
import com.safetynet.safetynetalerts.repository.ReadMedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.ReadPersonRepository;
import com.safetynet.safetynetalerts.repository.WriteFireStationRepository;
import java.lang.reflect.Method;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
  private WriteFireStationRepository writeFireStationRepository;

  @Mock
  private CalculateAgeServiceInterface calculateAgeService;

  @InjectMocks
  private FireStationService fireStationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Nested
  public class GetPersonsCoveredByStation {

    @Test
    void testGetPersonsCoveredByStation_getAdultCount() {
      List<FireStation> fireStations = Arrays.asList(new FireStation());

      List<Person> persons = Arrays.asList(new Person(), new Person());

      MedicalRecord medicalRecord = new MedicalRecord();
      medicalRecord.setBirthdate("01/01/2000");

      when(readFireStationRepository.findByStationNumberToList("1")).thenReturn(
        fireStations
      );
      when(
        readPersonRepository.findPersonsByAddresses(fireStations)
      ).thenReturn(persons);
      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(
          any(Person.class)
        )
      ).thenReturn(Optional.of(medicalRecord));
      when(calculateAgeService.calculate(anyString())).thenReturn(21);
      when(calculateAgeService.isChild(21)).thenReturn(false);

      FireStationDTO result = fireStationService.getPersonsCoveredByStation(
        "1"
      );

      assertEquals(2, result.getAdultCount());

      verify(readFireStationRepository, times(1)).findByStationNumberToList(
        "1"
      );
      verify(readPersonRepository, times(1)).findPersonsByAddresses(
        fireStations
      );
    }

    @Test
    void testGetPersonsCoveredByStation_getChildCount() {
      List<FireStation> fireStations = Arrays.asList(new FireStation());

      List<Person> persons = Arrays.asList(new Person(), new Person());

      MedicalRecord medicalRecord = new MedicalRecord();
      medicalRecord.setBirthdate("01/01/2010");

      when(readFireStationRepository.findByStationNumberToList("1")).thenReturn(
        fireStations
      );
      when(
        readPersonRepository.findPersonsByAddresses(fireStations)
      ).thenReturn(persons);
      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(
          any(Person.class)
        )
      ).thenReturn(Optional.of(medicalRecord));
      when(calculateAgeService.calculate(anyString())).thenReturn(11);
      when(calculateAgeService.isChild(11)).thenReturn(true);

      FireStationDTO result = fireStationService.getPersonsCoveredByStation(
        "1"
      );

      assertEquals(2, result.getChildCount());

      verify(readFireStationRepository, times(1)).findByStationNumberToList(
        "1"
      );
      verify(readPersonRepository, times(1)).findPersonsByAddresses(
        fireStations
      );
    }

    @Test
    void testGetPersonsCoveredByStation_NoMedicalRecord() {
      List<FireStation> fireStations = Arrays.asList(new FireStation());

      List<Person> persons = Arrays.asList(new Person(), new Person());

      when(readFireStationRepository.findByStationNumberToList("1")).thenReturn(
        fireStations
      );
      when(
        readPersonRepository.findPersonsByAddresses(fireStations)
      ).thenReturn(persons);
      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(
          any(Person.class)
        )
      ).thenReturn(Optional.empty());

      FireStationDTO result = fireStationService.getPersonsCoveredByStation(
        "1"
      );

      assertEquals(0, result.getAdultCount());
      assertEquals(0, result.getChildCount());

      verify(readFireStationRepository, times(1)).findByStationNumberToList(
        "1"
      );
      verify(readPersonRepository, times(1)).findPersonsByAddresses(
        fireStations
      );
    }
  }

  @Nested
  public class GetPhoneNumbersByFirestation {

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
    void testGetPhoneNumbersByFirestation_NoFireStation() {
      String stationNumber = "1";
      List<FireStation> fireStations = Arrays.asList(
        new FireStation(),
        new FireStation()
      );

      when(
        readFireStationRepository.findByStationNumberToList(stationNumber)
      ).thenReturn(fireStations);
      when(
        readPersonRepository.findPhoneNumbersByAddress(fireStations)
      ).thenReturn(new HashSet<>());

      Set<String> result = fireStationService.getPhoneNumbersByFireStation(
        stationNumber
      );

      assertEquals(0, result.size());
      verify(readFireStationRepository, times(1)).findByStationNumberToList(
        stationNumber
      );
      verify(readPersonRepository, times(1)).findPhoneNumbersByAddress(
        fireStations
      );
    }
  }

  @Nested
  public class GetFireInfoByAddress {

    @Test
    void testGetFireInfoByAddress() {
      FireStation fireStation = new FireStation();
      fireStation.setStation("1");
      List<Person> persons = Arrays.asList(new Person(), new Person());
      MedicalRecord medicalRecord = new MedicalRecord();
      medicalRecord.setBirthdate("01/01/2000");

      when(
        readFireStationRepository.findByStationAddress("address")
      ).thenReturn(Optional.of(fireStation));
      when(readPersonRepository.findPersonsAtAddress("address")).thenReturn(
        persons
      );
      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(
          any(Person.class)
        )
      ).thenReturn(Optional.of(medicalRecord));
      when(calculateAgeService.calculate(anyString())).thenReturn(21);

      FireDTO result = fireStationService.getFireInfoByAddress("address");

      assertEquals("1", result.getStationNumber());
      assertEquals(2, result.getPersons().size());
      verify(readFireStationRepository, times(1)).findByStationAddress(
        "address"
      );
      verify(readPersonRepository, times(1)).findPersonsAtAddress("address");
    }

    @Test
    void testGetFireInfoByAddress_NoFireStation() {
      when(
        readFireStationRepository.findByStationAddress("address")
      ).thenReturn(Optional.empty());

      FireDTO result = fireStationService.getFireInfoByAddress("address");

      assertNull(result.getStationNumber());
      assertEquals(0, result.getPersons().size());
      verify(readFireStationRepository, times(1)).findByStationAddress(
        "address"
      );
    }

    @Test
    void testGetFireInfoByAddress_NoMedicalRecord() {
      FireStation fireStation = new FireStation();
      fireStation.setStation("1");
      List<Person> persons = Arrays.asList(new Person(), new Person());

      when(
        readFireStationRepository.findByStationAddress("address")
      ).thenReturn(Optional.of(fireStation));
      when(readPersonRepository.findPersonsAtAddress("address")).thenReturn(
        persons
      );
      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(
          any(Person.class)
        )
      ).thenReturn(Optional.empty());

      FireDTO result = fireStationService.getFireInfoByAddress("address");

      assertEquals("1", result.getStationNumber());
      assertEquals(0, result.getPersons().size());
      verify(readFireStationRepository, times(1)).findByStationAddress(
        "address"
      );
      verify(readPersonRepository, times(1)).findPersonsAtAddress("address");
    }
  }

  @Nested
  public class GetHouseholdsByStations {

    @Test
    void testGetHouseholdsByStations() {
      Set<String> addresses = new HashSet<>(
        Arrays.asList("address1", "address2")
      );
      Map<String, List<Person>> groupedPersons = new HashMap<>();
      groupedPersons.put("address1", Arrays.asList(new Person(), new Person()));
      groupedPersons.put("address2", Arrays.asList(new Person()));

      when(
        readFireStationRepository.findAllStationsNumberToSet(
          Arrays.asList("1", "2")
        )
      ).thenReturn(addresses);
      when(
        readPersonRepository.findAndGroupPersonsByAddress(addresses)
      ).thenReturn(groupedPersons);

      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(
          any(Person.class)
        )
      ).thenReturn(Optional.of(new MedicalRecord()));
      when(calculateAgeService.calculate(anyString())).thenReturn(21);

      List<HouseholdInfoDTO> result =
        fireStationService.getHouseholdsByStations(Arrays.asList("1", "2"));

      assertEquals(2, result.size());
      verify(readFireStationRepository, times(1)).findAllStationsNumberToSet(
        Arrays.asList("1", "2")
      );
      verify(readPersonRepository, times(1)).findAndGroupPersonsByAddress(
        addresses
      );
    }

    @Test
    void testGetHouseholdsByStations_NoAddresses() {
      when(
        readFireStationRepository.findAllStationsNumberToSet(
          Arrays.asList("1", "2")
        )
      ).thenReturn(new HashSet<>());

      List<HouseholdInfoDTO> result =
        fireStationService.getHouseholdsByStations(Arrays.asList("1", "2"));

      assertEquals(0, result.size());
      verify(readFireStationRepository, times(1)).findAllStationsNumberToSet(
        Arrays.asList("1", "2")
      );
      verify(readPersonRepository, times(1)).findAndGroupPersonsByAddress(
        any()
      );
    }
  }

  @Nested
  public class AddFireStation {

    @Test
    void testAddFireStation() {
      FireStation fireStation = new FireStation();
      fireStation.setStation("1");
      fireStation.setAddress("address");

      fireStationService.addFireStation(fireStation);

      verify(writeFireStationRepository, times(1)).save(fireStation);
    }

    @Test
    void testAddFireStation_NoAddress() {
      FireStation fireStation = new FireStation();
      fireStation.setAddress(null);
      fireStation.setStation("1");

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> {
          fireStationService.addFireStation(fireStation);
        }
      );

      assertEquals("Address and station are required", exception.getMessage());
      verify(writeFireStationRepository, times(0)).save(fireStation);
    }

    @Test
    void testAddFireStation_NoStation() {
      FireStation fireStation = new FireStation();
      fireStation.setAddress("address");
      fireStation.setStation(null);

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> {
          fireStationService.addFireStation(fireStation);
        }
      );

      assertEquals("Address and station are required", exception.getMessage());
      verify(writeFireStationRepository, times(0)).save(fireStation);
    }
  }

  @Nested
  public class UpdateFireStation {

    @Test
    void testUpdateFireStation() {
      FireStation fireStation = new FireStation();
      fireStation.setStation("1");
      fireStation.setAddress("address");

      fireStationService.updateFireStation(fireStation);

      verify(writeFireStationRepository, times(1)).update(fireStation);
    }

    @Test
    void testUpdateFireStation_NoAddress() {
      FireStation fireStation = new FireStation();
      fireStation.setAddress(null);
      fireStation.setStation("1");

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> {
          fireStationService.updateFireStation(fireStation);
        }
      );

      assertEquals("Address and station are required", exception.getMessage());
      verify(writeFireStationRepository, times(0)).update(fireStation);
    }

    @Test
    void testUpdateFireStation_NoStation() {
      FireStation fireStation = new FireStation();
      fireStation.setAddress("address");
      fireStation.setStation(null);

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> {
          fireStationService.updateFireStation(fireStation);
        }
      );

      assertEquals("Address and station are required", exception.getMessage());
      verify(writeFireStationRepository, times(0)).update(fireStation);
    }
  }

  @Nested
  public class DeleteFireStation {

    @Test
    void testDeleteFireStation() {
      FireStation fireStation = new FireStation();
      fireStation.setStation("1");
      fireStation.setAddress("address");

      fireStationService.deleteFireStation(fireStation);

      verify(writeFireStationRepository, times(1)).delete(fireStation);
    }

    @Test
    void testDeleteFireStation_NoAddress() {
      FireStation fireStation = new FireStation();
      fireStation.setAddress(null);
      fireStation.setStation("1");

      IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> {
          fireStationService.deleteFireStation(fireStation);
        }
      );

      assertEquals("Address is required", exception.getMessage());
      verify(writeFireStationRepository, times(0)).delete(fireStation);
    }

    @Test
    void testDeleteFireStation_NoStation() {
      FireStation fireStation = new FireStation();
      fireStation.setAddress("address");
      fireStation.setStation(null);

      fireStationService.deleteFireStation(fireStation);

      verify(writeFireStationRepository, times(1)).delete(fireStation);
    }
  }

  @Nested
  public class CreatePersonInfoDTO_PrivateMethod {

    private Person person;

    @BeforeEach
    void setUp() {
      person = new Person();
      person.setFirstName("John");
      person.setLastName("Doe");
      person.setPhone("123-456-7890");
    }

    @Test
    void testCreatePersonInfoDTO_MedicalRecordPresent() throws Exception {
      MedicalRecord medicalRecord = new MedicalRecord();
      medicalRecord.setFirstName("John");
      medicalRecord.setLastName("Doe");
      medicalRecord.setBirthdate("01/01/2000");
      medicalRecord.setMedications(Arrays.asList("med1", "med2"));
      medicalRecord.setAllergies(Arrays.asList("allergy1"));

      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(
          any(Person.class)
        )
      ).thenReturn(Optional.of(medicalRecord));
      when(calculateAgeService.calculate(anyString())).thenReturn(25);

      Map.Entry<String, List<Person>> entry = new AbstractMap.SimpleEntry<>(
        "123 Main St",
        Collections.singletonList(person)
      );

      Method method =
        FireStationService.class.getDeclaredMethod(
            "createPersonInfoDTO",
            Map.Entry.class
          );
      method.setAccessible(true);

      @SuppressWarnings("unchecked")
      List<PersonInfoDTO> result = (List<PersonInfoDTO>) method.invoke(
        fireStationService,
        entry
      );

      assertNotNull(result);
      assertEquals(1, result.size());

      PersonInfoDTO personInfoDTO = result.get(0);
      assertEquals("John", personInfoDTO.getFirstName());
      assertEquals("Doe", personInfoDTO.getLastName());
      assertEquals("123-456-7890", personInfoDTO.getPhone());
      assertEquals(25, personInfoDTO.getAge());
      assertEquals(
        Arrays.asList("med1", "med2"),
        personInfoDTO.getMedications()
      );
      assertEquals(Arrays.asList("allergy1"), personInfoDTO.getAllergies());

      verify(readMedicalRecordRepository, times(1)).findByFirstNameAndLastName(
        person
      );
      verify(calculateAgeService, times(1)).calculate("01/01/2000");
    }

    @Test
    void testCreatePersonInfoDTO_MedicalRecordAbsent() throws Exception {
      when(
        readMedicalRecordRepository.findByFirstNameAndLastName(
          any(Person.class)
        )
      ).thenReturn(Optional.empty());

      Map.Entry<String, List<Person>> entry = new AbstractMap.SimpleEntry<>(
        "123 Main St",
        Collections.singletonList(person)
      );

      Method method =
        FireStationService.class.getDeclaredMethod(
            "createPersonInfoDTO",
            Map.Entry.class
          );
      method.setAccessible(true);

      @SuppressWarnings("unchecked")
      List<PersonInfoDTO> result = (List<PersonInfoDTO>) method.invoke(
        fireStationService,
        entry
      );

      assertNotNull(result);
      assertEquals(1, result.size());

      PersonInfoDTO personInfoDTO = result.get(0);
      assertEquals("John", personInfoDTO.getFirstName());
      assertEquals("Doe", personInfoDTO.getLastName());
      assertEquals("123-456-7890", personInfoDTO.getPhone());
      assertEquals(0, personInfoDTO.getAge());
      assertNull(personInfoDTO.getMedications());
      assertNull(personInfoDTO.getAllergies());

      verify(readMedicalRecordRepository, times(1)).findByFirstNameAndLastName(
        person
      );
      verify(calculateAgeService, never()).calculate(anyString());
    }
  }
}
