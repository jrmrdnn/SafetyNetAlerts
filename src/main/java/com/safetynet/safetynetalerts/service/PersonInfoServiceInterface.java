package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.dto.PersonInfoDTO;

public interface PersonInfoServiceInterface {

    /**
     * Get a list of persons with the given last name and their information
     * 
     * @param lastName the last name of the persons
     * @return a list of PersonInfoDTO
     */
    List<PersonInfoDTO> getPersonInfoByLastName(String lastName);

}