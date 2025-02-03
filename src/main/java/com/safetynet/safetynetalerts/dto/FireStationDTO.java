package com.safetynet.safetynetalerts.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireStationDTO {
    private List<PersonInfoDTO> persons;
    private int adultCount;
    private int childCount;

}
