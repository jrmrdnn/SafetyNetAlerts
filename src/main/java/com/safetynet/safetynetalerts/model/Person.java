package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

@Data
public class Person {
    @JsonProperty("firstName")
    @Pattern(regexp = "^[a-zA-ZàâçéèêëïùüÀÇÉÈÊËÏÙÜ\\s-]*$", message = "First name must contain only letters, spaces and hyphens")
    private String firstName;

    @JsonProperty("lastName")
    @Pattern(regexp = "^[a-zA-ZàâçéèêëïùüÀÇÉÈÊËÏÙÜ\\s-]*$", message = "Last name must contain only letters, spaces and hyphens")
    private String lastName;

    @JsonProperty("address")
    @Pattern(regexp = "^[0-9a-zA-ZàâçéèêëïùüÀÇÉÈÊËÏÙÜ\\s-.]*$", message = "Address must contain only letters, numbers, spaces and hyphens")
    private String address;

    @JsonProperty("city")
    @Pattern(regexp = "^[a-zA-ZàâçéèêëïùüÀÇÉÈÊËÏÙÜ\\s-]*$", message = "City must contain only letters, spaces and hyphens")
    private String city;

    @JsonProperty("zip")
    @Pattern(regexp = "^\\d{5}$", message = "Zip must be a 5-digit number")
    private String zip;

    @JsonProperty("phone")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Phone must be in the format xxx-xxx-xxxx")
    private String phone;

    @JsonProperty("email")
    @Email(message = "Email must be a valid email address")
    private String email;
}