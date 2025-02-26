package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;

public interface WriteFireStationService {
  /**
   * Add a fire station.
   * @param firestation
   */
  void addFireStation(FireStation firestation);

  /**
   * Update a fire station.
   * @param fireStation
   */
  void updateFireStation(FireStation fireStation);

  /**
   * Delete a fire station.
   * @param fireStation
   */
  void deleteFireStation(FireStation fireStation);
}
