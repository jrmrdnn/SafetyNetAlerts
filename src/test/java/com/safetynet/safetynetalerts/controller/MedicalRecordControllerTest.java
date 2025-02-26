package com.safetynet.safetynetalerts.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.WriteMedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MedicalRecordControllerTest {

  private MockMvc mockMvc;
  private MedicalRecord medicalRecord1;
  private MedicalRecord medicalRecord2;

  @Mock
  private WriteMedicalRecordService writeMedicalRecordService;

  @InjectMocks
  private MedicalRecordController medicalRecordController;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(medicalRecordController).build();

    medicalRecord1 = new MedicalRecord();
    medicalRecord1.setFirstName("John");
    medicalRecord1.setLastName("Doe");
    medicalRecord1.setBirthdate("01/01/2000");

    medicalRecord2 = new MedicalRecord();
    medicalRecord2.setFirstName("Jane");
    medicalRecord2.setLastName("Doe");
    medicalRecord2.setBirthdate("02/02/2000");
  }

  @Test
  public void testAddMedicalRecord() throws Exception {
    mockMvc
      .perform(
        post("/medicalRecord")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(medicalRecord1))
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.firstName").value("John"));

    verify(writeMedicalRecordService, times(1)).addMedicalRecord(
      any(MedicalRecord.class)
    );
  }

  @Test
  public void testUpdateMedicalRecord() throws Exception {
    mockMvc
      .perform(
        put("/medicalRecord")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(medicalRecord1))
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.firstName").value("John"));

    verify(writeMedicalRecordService, times(1)).updateMedicalRecord(
      any(MedicalRecord.class)
    );
  }

  @Test
  public void testDeleteMedicalRecord() throws Exception {
    mockMvc
      .perform(
        delete("/medicalRecord")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(medicalRecord1))
      )
      .andExpect(status().isOk())
      .andExpect(content().string("Medical record deleted: Doe John"));

    verify(writeMedicalRecordService, times(1)).deleteMedicalRecord(
      any(MedicalRecord.class)
    );
  }
}
