package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FireStation {
    @JsonProperty("address")
    @Pattern(regexp = "^[0-9a-zA-ZàâçéèêëïùüÀÇÉÈÊËÏÙÜ\\s-.]*$", message = "Address must contain only letters, numbers, spaces and hyphens")
    private String address;

    @JsonProperty("station")
    @Pattern(regexp = "\\d+", message = "Station must be a number")
    private String station;
}
