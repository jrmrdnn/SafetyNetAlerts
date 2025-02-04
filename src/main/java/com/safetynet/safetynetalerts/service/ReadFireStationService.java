package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireStationDTO;

public interface ReadFireStationService {
  /**
   * Get persons covered by a fire station.
   * @param station
   * @return
   */
  FireStationDTO getPersonsCoveredByStation(String station);
}
