package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;
import com.safetynet.safetynetalerts.service.FloodService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FloodController {

    private static final Logger logger = LogManager.getLogger(FloodController.class);

    @Autowired
    private FloodService floodService;

    @GetMapping("/flood/stations")
    public List<HouseholdInfoDTO> getFloodStations(@RequestParam List<String> stations) {
        logger.info("GET /flood/stations?station=" + stations);
        List<HouseholdInfoDTO> households = floodService.getHouseholdsByStations(stations);
        logger.info("Response: " + households);
        return households;
    }

}
