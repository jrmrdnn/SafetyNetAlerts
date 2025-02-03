package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.model.MedicalRecord;

public interface MedicalRecordServiceInterface {

    /**
     * Get all medical records from the JSON file
     * 
     * @return list of medical records
     */
    List<MedicalRecord> getAllMedicalRecords();

}