package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireStationDTO;
import java.util.Set;

public interface ReadFireStationService {
  /**
   * Get persons covered by a fire station.
   * @param station
   * @return
   */
  FireStationDTO getPersonsCoveredByStation(String station);

  /**
   * Get phone numbers by fire station.
   * @param stationNumber
   * @return
   */
  Set<String> getPhoneNumbersByFireStation(String stationNumber);

  /**
   * Get fire information by address.
   * @param address
   * @return
   */
  public FireDTO getFireInfoByAddress(String address);
}
