package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReadFireStationRepository {
  /**
   * Find addresses by station number.
   * @param stationNumber
   * @return
   */
  List<FireStation> findByStationNumberToList(String stationNumber);

  /**
   * Find station by address.
   * @param address
   * @return
   */
  Optional<FireStation> findByStationAddress(String address);

  /**
   * Find all stations number to set.
   * @param stations
   * @return
   */
  Set<String> findAllStationsNumberToSet(List<String> stations);
}
