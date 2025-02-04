package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.CommunityEmailServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class CommunityEmailController {

    private static final Logger logger = LogManager.getLogger(CommunityEmailController.class);

    @Autowired
    private CommunityEmailServiceInterface communityEmailService;

    @GetMapping("/communityEmail")
    public Set<String> getCommunityEmails(@RequestParam String city) {
        logger.info("GET /communityEmail?city=" + city);
        Set<String> emailsByCity = communityEmailService.getEmailsByCity(city);
        logger.info("Response: " + emailsByCity.toString());
        return emailsByCity;
    }
}
