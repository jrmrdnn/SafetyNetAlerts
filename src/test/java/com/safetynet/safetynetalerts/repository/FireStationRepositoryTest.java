package com.safetynet.safetynetalerts.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.service.DataPersistenceServiceInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FireStationRepositoryTest {

  private FireStation fireStation1;
  private FireStation fireStation2;

  @Mock
  private JsonWrapper jsonWrapper;

  @Mock
  private DataPersistenceServiceInterface dataPersistenceService;

  @InjectMocks
  private FireStationRepository fireStationRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    fireStation1 = new FireStation();
    fireStation1.setStation("1");
    fireStation1.setAddress("123 Main St");

    fireStation2 = new FireStation();
    fireStation2.setStation("2");
    fireStation2.setAddress("456 Elm St");
  }

  @Test
  public void testFindByStationNumberToList() {
    when(jsonWrapper.getFireStations()).thenReturn(
      Arrays.asList(fireStation1, fireStation2)
    );

    List<FireStation> result = fireStationRepository.findByStationNumberToList(
      "1"
    );

    assertEquals(1, result.size());
    assertTrue(result.contains(fireStation1));
  }

  @Test
  public void testFindByStationAddress() {
    when(jsonWrapper.getFireStations()).thenReturn(
      Arrays.asList(fireStation1, fireStation2)
    );

    Optional<FireStation> result = fireStationRepository.findByStationAddress(
      "123 Main St"
    );

    assertTrue(result.isPresent());
    assertEquals(fireStation1, result.get());
  }

  @Test
  public void testFindAllStationsNumberToSet() {
    when(jsonWrapper.getFireStations()).thenReturn(
      Arrays.asList(fireStation1, fireStation2)
    );

    Set<String> result = fireStationRepository.findAllStationsNumberToSet(
      Arrays.asList("1", "2")
    );

    assertEquals(2, result.size());
    assertTrue(result.contains("123 Main St"));
    assertTrue(result.contains("456 Elm St"));
  }

  @Test
  public void testSave() {
    List<FireStation> fireStations = new ArrayList<>();
    when(jsonWrapper.getFireStations()).thenReturn(fireStations);

    fireStationRepository.save(fireStation1);

    verify(jsonWrapper, times(2)).getFireStations();
    verify(dataPersistenceService).saveData();
    assertTrue(fireStations.contains(fireStation1));
  }

  @Test
  public void testSaveExistingFireStation() {
    List<FireStation> fireStations = new ArrayList<>(
      Collections.singletonList(fireStation1)
    );
    when(jsonWrapper.getFireStations()).thenReturn(fireStations);

    assertThrows(IllegalArgumentException.class, () ->
      fireStationRepository.save(fireStation1)
    );
  }

  @Test
  public void testUpdate() {
    FireStation updatedFireStation = new FireStation();
    updatedFireStation.setStation("2");
    updatedFireStation.setAddress("123 Main St");

    List<FireStation> fireStations = new ArrayList<>(
      Collections.singletonList(fireStation1)
    );

    when(jsonWrapper.getFireStations()).thenReturn(fireStations);

    fireStationRepository.update(updatedFireStation);

    verify(jsonWrapper, times(1)).getFireStations();
    verify(dataPersistenceService).saveData();

    assertTrue(fireStations.get(0).getStation().equals("2"));
    assertTrue(fireStations.get(0).getAddress().equals("123 Main St"));
  }

  @Test
  public void testUpdateNonExistingFireStation() {
    List<FireStation> fireStations = new ArrayList<>(
      Collections.singletonList(fireStation1)
    );
    when(jsonWrapper.getFireStations()).thenReturn(fireStations);

    assertThrows(IllegalArgumentException.class, () ->
      fireStationRepository.update(fireStation2)
    );
  }

  @Test
  public void testDelete() {
    List<FireStation> fireStations = new ArrayList<>(
      Collections.singletonList(fireStation1)
    );
    when(jsonWrapper.getFireStations()).thenReturn(fireStations);

    fireStationRepository.delete(fireStation1);

    verify(jsonWrapper, times(2)).getFireStations();
    verify(dataPersistenceService).saveData();
    assertFalse(fireStations.contains(fireStation1));
  }

  @Test
  public void testDeleteNonExistingFireStation() {
    List<FireStation> fireStations = new ArrayList<>(
      Collections.singletonList(fireStation1)
    );
    when(jsonWrapper.getFireStations()).thenReturn(fireStations);

    assertThrows(IllegalArgumentException.class, () ->
      fireStationRepository.delete(fireStation2)
    );
  }
}
