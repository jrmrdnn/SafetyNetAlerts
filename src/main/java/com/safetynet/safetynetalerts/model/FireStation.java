package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.safetynetalerts.constants.RegexpConstants;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FireStation {

  @JsonProperty("address")
  @Pattern(
    regexp = RegexpConstants.TEXT_REGEXP,
    message = "Address must contain only letters, numbers, spaces, hyphens, apostrophes and dots"
  )
  private String address;

  @JsonProperty("station")
  @Pattern(
    regexp = RegexpConstants.NUMBER_REGEXP,
    message = "Station must be a number"
  )
  private String station;
}
