package com.safetynet.safetynetalerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.dto.HouseholdInfoDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.ReadFireStationService;
import com.safetynet.safetynetalerts.service.WriteFireStationService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class FireStationControllerTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper = new ObjectMapper();

  @Mock
  private ReadFireStationService readFireStationService;

  @Mock
  private WriteFireStationService writeFireStationService;

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

  @Test
  public void testGetFireInfo() throws Exception {
    String address = "123 Test St";

    FireDTO fireDTO = new FireDTO();

    when(readFireStationService.getFireInfoByAddress(address)).thenReturn(
      fireDTO
    );

    mockMvc
      .perform(get("/fire").param("address", address))
      .andExpect(status().isOk())
      .andExpect(content().json("{/* expected JSON response */}"));
  }

  @Test
  public void testGetFloodStations() throws Exception {
    List<String> stations = Arrays.asList("1", "2");
    List<HouseholdInfoDTO> households = Arrays.asList(
      new HouseholdInfoDTO(),
      new HouseholdInfoDTO()
    );

    when(readFireStationService.getHouseholdsByStations(stations)).thenReturn(
      households
    );

    mockMvc
      .perform(get("/flood/stations").param("stations", "1,2"))
      .andExpect(status().isOk())
      .andExpect(content().json("[{}, {}]"));
  }

  @Test
  public void testAddFireStation() throws Exception {
    FireStation fireStation = new FireStation();
    fireStation.setStation("1");
    fireStation.setAddress("123 Test St");

    mockMvc
      .perform(
        post("/firestation")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(fireStation))
      )
      .andExpect(status().isOk())
      .andExpect(content().json("{/* expected JSON response */}"));
  }

  @Test
  public void testUpdateFireStation() throws Exception {
    FireStation fireStation = new FireStation();
    fireStation.setStation("1");
    fireStation.setAddress("123 Test St");

    mockMvc
      .perform(
        put("/firestation")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(fireStation))
      )
      .andExpect(status().isOk())
      .andExpect(content().json("{/* expected JSON response */}"));
  }

  @Test
  public void testDeleteFireStation() throws Exception {
    FireStation fireStation = new FireStation();
    fireStation.setStation("1");
    fireStation.setAddress("123 Test St");

    mockMvc
      .perform(
        delete("/firestation")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(fireStation))
      )
      .andExpect(status().isOk())
      .andExpect(
        content().string("FireStation deleted: " + fireStation.getAddress())
      );
  }
}
