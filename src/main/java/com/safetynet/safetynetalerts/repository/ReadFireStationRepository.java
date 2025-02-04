package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import java.util.List;

public interface ReadFireStationRepository {
  /**
   * Find addresses by station number.
   * @param stationNumber
   * @return
   */
  List<FireStation> findByStationNumberToList(String stationNumber);
}
