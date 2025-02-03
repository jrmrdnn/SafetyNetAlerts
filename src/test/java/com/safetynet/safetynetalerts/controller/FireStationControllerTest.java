package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.service.FireStationServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FireStationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FireStationServiceInterface firestationService;

    @InjectMocks
    private FireStationController fireStationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fireStationController).build();
    }

    @Test
    public void testGetFireStation() throws Exception {
        String stationNumber = "1";
        FireStationDTO fireStationDTO = new FireStationDTO();

        when(firestationService.getPersonsCoveredByStation(stationNumber)).thenReturn(fireStationDTO);

        mockMvc.perform(
                get("/firestation").param("stationNumber", stationNumber).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json("{/* expected JSON response */}"));
    }
}