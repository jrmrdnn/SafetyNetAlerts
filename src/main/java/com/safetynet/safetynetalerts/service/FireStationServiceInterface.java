package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireStationDTO;

public interface FireStationServiceInterface {

    /**
     * Get the list of persons covered by a fire station
     * 
     * @param stationNumber number of the fire station
     * @return list of persons covered by the fire station
     */
    FireStationDTO getPersonsCoveredByStation(String stationNumber);

}