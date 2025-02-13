package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.ReadPersonService;
import com.safetynet.safetynetalerts.service.WritePersonService;
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
public class PersonController {

  private final ReadPersonService readPersonService;
  private final WritePersonService writePersonService;

  PersonController(
    ReadPersonService readPersonService,
    WritePersonService writePersonService
  ) {
    this.readPersonService = readPersonService;
    this.writePersonService = writePersonService;
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

  @PostMapping("/person")
  public Person addPerson(@Valid @RequestBody Person person) {
    writePersonService.addPerson(person);
    return person;
  }

  @PutMapping("/person")
  public Person updatePerson(@Valid @RequestBody Person person) {
    writePersonService.updatePerson(person);
    return person;
  }

  @DeleteMapping("/person")
  public String deletePerson(@Valid @RequestBody Person person) {
    writePersonService.deletePerson(person);
    return (
      "Person deleted: " + person.getFirstName() + " " + person.getLastName()
    );
  }
}
