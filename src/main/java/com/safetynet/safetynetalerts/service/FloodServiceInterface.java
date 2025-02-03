package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;

public interface FloodServiceInterface {

    /**
     * Get the list of households covered by a list of fire stations
     * 
     * @param stationNumbers list of fire station numbers
     * @return list of households covered by the fire stations
     */
    List<HouseholdInfoDTO> getHouseholdsByStations(List<String> stationNumbers);

}