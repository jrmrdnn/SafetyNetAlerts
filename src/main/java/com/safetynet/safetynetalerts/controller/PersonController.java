package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.service.ReadPersonService;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

  private final ReadPersonService readPersonService;

  PersonController(ReadPersonService readPersonService) {
    this.readPersonService = readPersonService;
  }

  @GetMapping("/childAlert")
  public List<ChildAlertDTO> getChildAlert(@RequestParam String address) {
    return readPersonService.allChildrenAtAddress(address);
  }

  @GetMapping("/personInfo")
  public List<PersonInfoDTO> getPersonInfo(@RequestParam String lastName) {
    return readPersonService.getPersonInfoByLastName(lastName);
  }

  @GetMapping("/communityEmail")
  public Set<String> getCommunityEmails(@RequestParam String city) {
    return readPersonService.getEmailsByCity(city);
  }
}
