package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.service.FireService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireController {

    private static final Logger logger = LogManager.getLogger(FireController.class);

    @Autowired
    private FireService fireService;

    @GetMapping("/fire")
    public FireDTO getFireInfo(@RequestParam String address) {
        logger.info("GET /fire?address=" + address);
        FireDTO fireInfoByAddress = fireService.getFireInfoByAddress(address);
        logger.info("Response: " + fireInfoByAddress.toString());
        return fireInfoByAddress;
    }
}
