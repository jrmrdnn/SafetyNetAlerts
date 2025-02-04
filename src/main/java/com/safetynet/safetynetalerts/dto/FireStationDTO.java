package com.safetynet.safetynetalerts.dto;

import com.safetynet.safetynetalerts.model.Person;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class FireStationDTO {

  private List<PersonDTO> persons;
  private int adultCount;
  private int childCount;

  public static FireStationDTO createFireStationDTO(
    List<Person> persons,
    int childCount,
    int adultCount
  ) {
    FireStationDTO fireStationDTO = new FireStationDTO();

    fireStationDTO.setPersons(
      persons
        .stream()
        .map(p -> {
          PersonDTO personDTO = new PersonDTO();
          personDTO.setFirstName(p.getFirstName());
          personDTO.setLastName(p.getLastName());
          personDTO.setAddress(p.getAddress());
          personDTO.setPhone(p.getPhone());
          return personDTO;
        })
        .collect(Collectors.toList())
    );

    fireStationDTO.setAdultCount(adultCount);
    fireStationDTO.setChildCount(childCount);

    return fireStationDTO;
  }
}
