package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;
import com.safetynet.safetynetalerts.service.ReadFireStationService;
import java.util.List;
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

  @GetMapping("/fire")
  public FireDTO getFireInfo(@RequestParam String address) {
    return readFireStationService.getFireInfoByAddress(address);
  }

  @GetMapping("/flood/stations")
  public List<HouseholdInfoDTO> getFloodStations(
    @RequestParam List<String> stations
  ) {
    return readFireStationService.getHouseholdsByStations(stations);
  }
}
