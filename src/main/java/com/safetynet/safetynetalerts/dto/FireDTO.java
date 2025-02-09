package com.safetynet.safetynetalerts.dto;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import java.util.List;
import lombok.Data;

@Data
public class FireDTO {

  private String stationNumber;
  private List<PersonDetails> persons;

  @Data
  public static class PersonDetails {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
  }

  public static PersonDetails createPersonDetails(
    Person person,
    int age,
    MedicalRecord medicalRecord
  ) {
    PersonDetails personDetails = new PersonDetails();

    personDetails.setFirstName(person.getFirstName());
    personDetails.setLastName(person.getLastName());
    personDetails.setPhone(person.getPhone());
    personDetails.setAge(age);
    personDetails.setMedications(medicalRecord.getMedications());
    personDetails.setAllergies(medicalRecord.getAllergies());

    return personDetails;
  }
}
