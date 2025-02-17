package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.WriteMedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

  private final WriteMedicalRecordService writeMedicalRecordService;

  MedicalRecordController(WriteMedicalRecordService writeMedicalRecordService) {
    this.writeMedicalRecordService = writeMedicalRecordService;
  }

  @PostMapping
  public MedicalRecord addMedicalRecord(
    @Valid @RequestBody MedicalRecord medicalRecord
  ) {
    writeMedicalRecordService.addMedicalRecord(medicalRecord);
    return medicalRecord;
  }

  @PutMapping
  public MedicalRecord updateMedicalRecord(
    @Valid @RequestBody MedicalRecord medicalRecord
  ) {
    writeMedicalRecordService.updateMedicalRecord(medicalRecord);
    return medicalRecord;
  }

  @DeleteMapping
  public String deleteMedicalRecord(
    @Valid @RequestBody MedicalRecord medicalRecord
  ) {
    writeMedicalRecordService.deleteMedicalRecord(medicalRecord);
    return (
      "Medical record deleted: " +
      medicalRecord.getLastName() +
      " " +
      medicalRecord.getFirstName()
    );
  }
}
