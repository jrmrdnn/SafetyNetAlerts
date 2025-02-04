package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class FireStationRepository implements ReadFireStationRepository {

  private final JsonWrapper jsonWrapper;

  public FireStationRepository(JsonWrapper jsonWrapper) {
    this.jsonWrapper = jsonWrapper;
  }

  @Override
  public List<FireStation> findByStationNumberToList(String stationNumber) {
    return jsonWrapper
      .getFireStations()
      .stream()
      .filter(f -> f.getStation().equals(stationNumber))
      .collect(Collectors.toList());
  }
}
