package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.safetynetalerts.constants.RegexpConstants;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.Data;

@Data
public class MedicalRecord {

  @JsonProperty("firstName")
  @Pattern(
    regexp = RegexpConstants.NAME_REGEXP,
    message = "First name must contain only letters, spaces, hyphens and apostrophes"
  )
  private String firstName;

  @JsonProperty("lastName")
  @Pattern(
    regexp = RegexpConstants.NAME_REGEXP,
    message = "Last name must contain only letters, spaces, hyphens and apostrophes"
  )
  private String lastName;

  @JsonProperty("birthdate")
  @Pattern(
    regexp = RegexpConstants.BIRTHDATE_REGEXP,
    message = "Birthdate must be in the format dd-MM-yyyy"
  )
  private String birthdate;

  @JsonProperty("medications")
  private List<String> medications;

  @JsonProperty("allergies")
  private List<String> allergies;
}
