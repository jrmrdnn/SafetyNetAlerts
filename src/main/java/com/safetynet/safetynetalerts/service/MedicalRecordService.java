package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService implements MedicalRecordServiceInterface {

    @Autowired
    private JsonWrapper jsonWrapper;

    /**
     * Get all medical records from the JSON file
     * 
     * @return list of medical records
     */
    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return jsonWrapper.getMedicalRecords();
    }
}
