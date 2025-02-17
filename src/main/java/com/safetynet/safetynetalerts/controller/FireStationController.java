package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationServiceInterface;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private static final Logger logger = LogManager.getLogger(ChildAlertController.class);

    @Autowired
    private FireStationServiceInterface firestationService;

    @GetMapping
    public FireStationDTO getFireStation(@RequestParam String stationNumber) {
        logger.info("Request: GET /firestation?stationNumber=" + stationNumber);
        FireStationDTO personsCoveredByStation = firestationService.getPersonsCoveredByStation(stationNumber);
        logger.info("Response: " + personsCoveredByStation.toString());
        return personsCoveredByStation;
    }

    @PostMapping
    public FireStation addFirestation(@Valid @RequestBody FireStation firestation) {
        logger.info("Request: POST /firestation");
        logger.info("Body: " + firestation.toString());
        FireStation addedFirestation = firestationService.addFireStation(firestation);
        logger.info("Response: " + addedFirestation.toString());
        return addedFirestation;
    }

    @PutMapping
    public FireStation updateFirestation(@Valid @RequestBody FireStation firestation) {
        logger.info("Request: PUT /firestation");
        logger.info("Body: " + firestation.toString());
        FireStation updatedFirestation = firestationService.updateFireStation(firestation);
        logger.info("Response: " + updatedFirestation.toString());
        return updatedFirestation;
    }

    @DeleteMapping
    public String deleteFirestation(@RequestBody FireStation firestation) {
        logger.info("Request: DELETE /firestation");
        logger.info("Body: " + firestation.toString());
        firestationService.deleteFireStation(firestation);
        String response = "FireStation deleted successfully: " + firestation.getAddress();
        logger.info(response);
        return response;
    }
}