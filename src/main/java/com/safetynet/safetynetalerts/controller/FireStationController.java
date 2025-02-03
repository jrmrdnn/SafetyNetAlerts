package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.service.FireStationServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class FireStationController {

    private static final Logger logger = Logger.getLogger(FireStationController.class.getName());

    @Autowired
    private FireStationServiceInterface firestationService;

    @GetMapping("/firestation")
    public FireStationDTO getFireStation(@RequestParam String stationNumber) {
        logger.info("GET /firestation?stationNumber=" + stationNumber);
        FireStationDTO personsCoveredByStation = firestationService.getPersonsCoveredByStation(stationNumber);
        logger.info("Response: " + personsCoveredByStation.toString());
        return personsCoveredByStation;
    }
}