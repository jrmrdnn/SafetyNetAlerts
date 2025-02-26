package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.service.ReadFireStationService;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

  private final ReadFireStationService readFireStationService;

  FireStationController(ReadFireStationService readFireStationService) {
    this.readFireStationService = readFireStationService;
  }

  @GetMapping("/firestation")
  public FireStationDTO getFireStation(@RequestParam String stationNumber) {
    return readFireStationService.getPersonsCoveredByStation(stationNumber);
  }

  @GetMapping("/phoneAlert")
  public Set<String> getPhoneAlert(@RequestParam String firestation) {
    return readFireStationService.getPhoneNumbersByFireStation(firestation);
  }
}
