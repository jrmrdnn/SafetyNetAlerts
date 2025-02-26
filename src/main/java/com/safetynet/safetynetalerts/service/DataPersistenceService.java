package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.constants.JacksonConstants;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.ReadJson;
import org.springframework.stereotype.Service;

@Service
public class DataPersistenceService implements DataPersistenceServiceInterface {

  private final JacksonServiceInterface jacksonService;
  private final ReadJson readJson;

  public DataPersistenceService(
    JacksonServiceInterface jacksonService,
    ReadJson readJson
  ) {
    this.jacksonService = jacksonService;
    this.readJson = readJson;
  }

  @Override
  public void saveData() {
    JsonWrapper data = new JsonWrapper();
    data.setPersons(readJson.getPersons());
    data.setFireStations(readJson.getFireStations());
    data.setMedicalRecords(readJson.getMedicalRecords());
    jacksonService.saveToFile(JacksonConstants.FILE_PATH, data);
  }
}
