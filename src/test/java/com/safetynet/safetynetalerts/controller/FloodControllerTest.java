package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;
import com.safetynet.safetynetalerts.service.FloodService;

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

import java.util.Arrays;
import java.util.List;

public class FloodControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FloodService floodService;

    @InjectMocks
    private FloodController floodController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(floodController).build();
    }

    @Test
    public void testGetFloodStations() throws Exception {
        List<String> stations = Arrays.asList("1", "2");
        List<HouseholdInfoDTO> households = Arrays.asList(new HouseholdInfoDTO(), new HouseholdInfoDTO());

        when(floodService.getHouseholdsByStations(stations)).thenReturn(households);

        mockMvc.perform(get("/flood/stations").param("stations", "1,2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json("[{}, {}]"));
    }
}