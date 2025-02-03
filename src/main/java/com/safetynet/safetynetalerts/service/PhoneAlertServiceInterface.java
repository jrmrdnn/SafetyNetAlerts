package com.safetynet.safetynetalerts.service;

import java.util.Set;

public interface PhoneAlertServiceInterface {

    /**
     * Get phone numbers of persons covered by a fire station
     * 
     * @param fireStationNumber the number of the fire station
     * @return a list of phone numbers
     */
    Set<String> getPhoneNumbersByFirestation(String fireStationNumber);

}