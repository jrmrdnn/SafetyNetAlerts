package com.safetynet.safetynetalerts.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import java.util.Arrays;
import java.util.List;
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
}
