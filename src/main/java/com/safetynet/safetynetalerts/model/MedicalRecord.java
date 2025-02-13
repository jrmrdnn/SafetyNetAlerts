package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecord {
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("birthdate")
    private String birthdate;

    @JsonProperty("medications")
    private List<String> medications;

    @JsonProperty("allergies")
    private List<String> allergies;
}
