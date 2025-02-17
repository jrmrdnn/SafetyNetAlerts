package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @PutMapping
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping
    public String deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.deleteMedicalRecord(medicalRecord);
        return "Medical record deleted successfully for: " + medicalRecord.getFirstName() + " "
                + medicalRecord.getLastName();
    }

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }
}
