package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class JsonWrapper implements ReadJson, WriteJson {

  @JsonProperty("persons")
  private List<Person> persons;

  @JsonProperty("firestations")
  private List<FireStation> fireStations;

  @JsonProperty("medicalrecords")
  private List<MedicalRecord> medicalRecords;
}
