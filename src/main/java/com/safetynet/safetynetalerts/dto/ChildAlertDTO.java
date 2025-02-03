package com.safetynet.safetynetalerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private int age;
    private List<HouseholdMember> householdMembers;

    @Data
    public static class HouseholdMember {
        private String firstName;
        private String lastName;
    }
}
