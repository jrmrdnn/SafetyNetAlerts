package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;

public interface ChildAlertServiceInterface {

    /**
     * Get children at address
     * 
     * @param address address
     * @return list of children at address
     */
    List<ChildAlertDTO> getChildrenAtAddress(String address);

}