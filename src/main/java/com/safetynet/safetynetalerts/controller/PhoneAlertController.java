package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.PhoneAlertService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class PhoneAlertController {

    private static final Logger logger = LogManager.getLogger(PhoneAlertController.class);

    @Autowired
    private PhoneAlertService phoneAlertService;

    @GetMapping("/phoneAlert")
    public Set<String> getPhoneAlert(@RequestParam String firestation) {
        logger.info("GET /phoneAlert?firestation=" + firestation);
        Set<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFirestation(firestation);
        logger.info("Response: " + phoneNumbers.toString());
        return phoneNumbers;
    }
}
