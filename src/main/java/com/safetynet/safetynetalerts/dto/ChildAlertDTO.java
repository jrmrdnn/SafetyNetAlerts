package com.safetynet.safetynetalerts.dto;

import com.safetynet.safetynetalerts.model.Person;
import java.util.List;
import lombok.Data;

@Data
public class ChildAlertDTO {

  private String firstName;
  private String lastName;
  private int age;
  private List<HouseholdMember> householdMembers;

  @Data
  public static class HouseholdMember {

    private String firstName;
    private String lastName;
  }

  public static ChildAlertDTO createChildAlertDTO(Person person, int age) {
    ChildAlertDTO child = new ChildAlertDTO();
    child.setFirstName(person.getFirstName());
    child.setLastName(person.getLastName());
    child.setAge(age);
    return child;
  }
}
