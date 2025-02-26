package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.ReadFireStationService;
import com.safetynet.safetynetalerts.service.WriteFireStationService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

  private final ReadFireStationService readFireStationService;
  private final WriteFireStationService writeFireStationService;

  FireStationController(
    ReadFireStationService readFireStationService,
    WriteFireStationService writeFireStationService
  ) {
    this.readFireStationService = readFireStationService;
    this.writeFireStationService = writeFireStationService;
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

  @PostMapping("/firestation")
  public FireStation addFireStation(
    @Valid @RequestBody FireStation fireStation
  ) {
    writeFireStationService.addFireStation(fireStation);
    return fireStation;
  }

  @PutMapping("/firestation")
  public FireStation updateFireStation(
    @Valid @RequestBody FireStation fireStation
  ) {
    writeFireStationService.updateFireStation(fireStation);
    return fireStation;
  }

  @DeleteMapping("/firestation")
  public String deleteFireStation(@Valid @RequestBody FireStation fireStation) {
    writeFireStationService.deleteFireStation(fireStation);
    return "FireStation deleted: " + fireStation.getAddress();
  }
}
