package com.safetynet.safetynetalerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class HouseholdInfoDTO {
    private String address;
    private List<PersonInfoDTO> persons;

    @Data
    public static class PersonInfoDTO {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private List<String> medications;
        private List<String> allergies;
    }
}
