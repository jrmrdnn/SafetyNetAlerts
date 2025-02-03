package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireStationDTO;

import java.util.List;

public interface FireStationServiceInterface {

    /**
     * Get the list of persons covered by a fire station
     * 
     * @param stationNumber number of the fire station
     * @return list of persons covered by the fire station
     */
    FireStationDTO getPersonsCoveredByStation(String stationNumber);

    /**
     * Get the list of addresses covered by a fire station
     * 
     * @param stationNumber number of the fire station
     * @return list of addresses covered by the fire station
     */
    List<String> getAddresses(String stationNumber);

}