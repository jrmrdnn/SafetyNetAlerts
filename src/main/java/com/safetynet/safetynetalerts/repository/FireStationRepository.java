package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.service.DataPersistenceServiceInterface;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class FireStationRepository
  implements ReadFireStationRepository, WriteFireStationRepository {

  private final JsonWrapper jsonWrapper;
  private final DataPersistenceServiceInterface dataPersistenceService;

  public FireStationRepository(
    JsonWrapper jsonWrapper,
    DataPersistenceServiceInterface dataPersistenceService
  ) {
    this.jsonWrapper = jsonWrapper;
    this.dataPersistenceService = dataPersistenceService;
  }

  @Override
  public List<FireStation> findByStationNumberToList(String stationNumber) {
    return jsonWrapper
      .getFireStations()
      .stream()
      .filter(f -> f.getStation().equals(stationNumber))
      .collect(Collectors.toList());
  }

  @Override
  public Optional<FireStation> findByStationAddress(String address) {
    return jsonWrapper
      .getFireStations()
      .stream()
      .filter(f -> f.getAddress().equalsIgnoreCase(address))
      .findFirst();
  }

  @Override
  public Set<String> findAllStationsNumberToSet(List<String> stations) {
    return jsonWrapper
      .getFireStations()
      .stream()
      .filter(f -> stations.contains(f.getStation()))
      .map(FireStation::getAddress)
      .collect(Collectors.toSet());
  }

  @Override
  public void save(FireStation fireStation) {
    Optional<FireStation> findStation = findByStationAddress(
      fireStation.getAddress()
    );

    if (findStation.isPresent()) {
      throw new IllegalArgumentException("FireStation already exists");
    }

    jsonWrapper.getFireStations().add(fireStation);
    dataPersistenceService.saveData();
  }

  @Override
  public void update(FireStation fireStation) {
    Optional<FireStation> findStation = findByStationAddress(
      fireStation.getAddress()
    );

    if (findStation.isEmpty()) {
      throw new IllegalArgumentException("FireStation not found");
    }

    jsonWrapper
      .getFireStations()
      .replaceAll(f -> {
        if (f.equals(findStation.get())) {
          FireStation updatedFireStation = new FireStation();
          updatedFireStation.setStation(
            fireStation.getStation() != null
              ? fireStation.getStation()
              : f.getStation()
          );
          updatedFireStation.setAddress(
            fireStation.getAddress() != null
              ? fireStation.getAddress()
              : f.getAddress()
          );
          return updatedFireStation;
        } else {
          return f;
        }
      });
    dataPersistenceService.saveData();
  }

  @Override
  public void delete(FireStation fireStation) {
    Optional<FireStation> findStation = findByStationAddress(
      fireStation.getAddress()
    );

    if (findStation.isEmpty()) {
      throw new IllegalArgumentException("FireStation not found");
    }

    jsonWrapper.getFireStations().remove(findStation.get());
    dataPersistenceService.saveData();
  }
}
