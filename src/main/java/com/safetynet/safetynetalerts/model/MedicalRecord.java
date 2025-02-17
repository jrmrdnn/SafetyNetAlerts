package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Data
public class MedicalRecord {
    @JsonProperty("firstName")
    @Pattern(regexp = "^[a-zA-ZàâçéèêëïùüÀÇÉÈÊËÏÙÜ\\s-]*$", message = "First name must contain only letters, spaces and hyphens")
    private String firstName;

    @JsonProperty("lastName")
    @Pattern(regexp = "^[a-zA-ZàâçéèêëïùüÀÇÉÈÊËÏÙÜ\\s-]*$", message = "Last name must contain only letters, spaces and hyphens")
    private String lastName;

    @JsonProperty("birthdate")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Birthdate must be in the format dd-MM-yyyy")
    private String birthdate;

    @JsonProperty("medications")
    private List<String> medications;

    @JsonProperty("allergies")
    private List<String> allergies;

}
