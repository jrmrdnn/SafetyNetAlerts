package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireDTO;

public interface FireServiceInterface {

    /**
     * Ge list of persons covered by a fire station
     * 
     * @param address the address
     * @return a list of persons
     */
    FireDTO getFireInfoByAddress(String address);

}