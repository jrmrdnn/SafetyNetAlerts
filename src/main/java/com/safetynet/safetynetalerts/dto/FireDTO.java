package com.safetynet.safetynetalerts.dto;

import java.util.List;

import lombok.Data;

@Data
public class FireDTO {
    private String stationNumber;
    private List<PersonDetails> persons;

    @Data
    public static class PersonDetails {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private List<String> medications;
        private List<String> allergies;
    }
}
