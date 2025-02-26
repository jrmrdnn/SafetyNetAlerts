package com.safetynet.safetynetalerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.service.ReadFireStationService;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class FireStationControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ReadFireStationService readFireStationService;

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

    when(
      readFireStationService.getPersonsCoveredByStation(stationNumber)
    ).thenReturn(fireStationDTO);

    mockMvc
      .perform(get("/firestation").param("stationNumber", stationNumber))
      .andExpect(status().isOk())
      .andExpect(content().json("{/* expected JSON response */}"));
  }

  @Test
  public void testGetPhoneAlert() throws Exception {
    String fireStation = "1";
    Set<String> phoneNumbers = new HashSet<>();
    phoneNumbers.add("123-456-7890");
    phoneNumbers.add("098-765-4321");

    when(
      readFireStationService.getPhoneNumbersByFireStation(fireStation)
    ).thenReturn(phoneNumbers);

    mockMvc
      .perform(get("/phoneAlert").param("firestation", fireStation))
      .andExpect(status().isOk())
      .andExpect(content().json("[\"123-456-7890\",\"098-765-4321\"]"));
  }
}
