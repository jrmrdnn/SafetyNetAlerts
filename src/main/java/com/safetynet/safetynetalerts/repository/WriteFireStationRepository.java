package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;

public interface WriteFireStationRepository {
  /**
   * Save a fire station
   *
   * @param fireStation
   */
  void save(FireStation fireStation);

  /**
   * Update a fire station
   *
   * @param fireStation
   */
  void update(FireStation fireStation);

  /**
   * Delete a fire station
   *
   * @param fireStation
   */
  void delete(FireStation fireStation);
}
