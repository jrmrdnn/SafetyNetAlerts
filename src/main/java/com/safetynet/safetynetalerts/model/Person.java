package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.safetynetalerts.constants.RegexpConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Person {

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

  @JsonProperty("address")
  @Pattern(
    regexp = RegexpConstants.TEXT_REGEXP,
    message = "Address must contain only letters, numbers, spaces, hyphens and apostrophes"
  )
  private String address;

  @JsonProperty("city")
  @Pattern(
    regexp = RegexpConstants.NAME_REGEXP,
    message = "City must contain only letters, spaces, hyphens and apostrophes"
  )
  private String city;

  @JsonProperty("zip")
  @Pattern(
    regexp = RegexpConstants.ZIP_REGEXP,
    message = "Zip must be a 5-digit number"
  )
  private String zip;

  @JsonProperty("phone")
  @Pattern(
    regexp = RegexpConstants.PHONE_REGEXP,
    message = "Phone must be in the format xxx-xxx-xxxx"
  )
  private String phone;

  @JsonProperty("email")
  @Email(message = "Email must be a valid email address")
  private String email;
}
