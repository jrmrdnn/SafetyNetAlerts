package com.safetynet.safetynetalerts.dto;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import java.util.List;
import lombok.Data;

@Data
public class HouseholdInfoDTO {

  private String address;
  private List<PersonInfoDTO> persons;

  @Data
  public static class PersonInfoDTO {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
  }

  public static PersonInfoDTO createPersonInfoDTO(
    Person person,
    int age,
    MedicalRecord medicalRecord
  ) {
    PersonInfoDTO personInfo = new PersonInfoDTO();
    personInfo.setFirstName(person.getFirstName());
    personInfo.setLastName(person.getLastName());
    personInfo.setPhone(person.getPhone());
    personInfo.setAge(age);
    personInfo.setMedications(medicalRecord.getMedications());
    personInfo.setAllergies(medicalRecord.getAllergies());
    return personInfo;
  }
}
