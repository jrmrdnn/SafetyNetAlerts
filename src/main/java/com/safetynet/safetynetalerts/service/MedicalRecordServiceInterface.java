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

    /**
     * Add a medical record to the JSON file
     * 
     * @param medicalRecord
     * @return the medical record added
     */
    MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Update a medical record in the JSON file
     * 
     * @param medicalRecord
     * @return the updated medical record
     */
    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Delete a medical record from the JSON file
     * 
     * @param firstName
     * @param lastName
     */
    void deleteMedicalRecord(MedicalRecord medicalRecord);
}