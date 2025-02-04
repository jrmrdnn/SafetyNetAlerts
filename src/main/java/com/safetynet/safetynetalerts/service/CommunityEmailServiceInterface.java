package com.safetynet.safetynetalerts.service;

import java.util.Set;

public interface CommunityEmailServiceInterface {

    /**
     * Get a list of emails of persons living in the city
     * 
     * @param city the city
     * @return a list of emails
     */
    Set<String> getEmailsByCity(String city);

}