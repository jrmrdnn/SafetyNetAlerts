package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.constants.JacksonConstants;
import com.safetynet.safetynetalerts.model.JsonWrapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService implements MedicalRecordServiceInterface {

    @Autowired
    private JsonWrapper jsonWrapper;

    @Autowired
    private JacksonServiceInterface jacksonService;

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return jsonWrapper.getMedicalRecords();
    }

    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = jsonWrapper.getMedicalRecords();
        Optional<MedicalRecord> existingMedicalRecord = getExistingMedicalRecord(medicalRecord.getFirstName(),
                medicalRecord.getLastName(), medicalRecords);

        if (existingMedicalRecord.isPresent()) {
            throw new IllegalArgumentException("Medical record already exists");
        }

        medicalRecords.add(medicalRecord);
        jsonWrapper.setMedicalRecords(medicalRecords);
        jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
        return medicalRecord;
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> allMedicalRecords = jsonWrapper.getMedicalRecords();
        Optional<MedicalRecord> MedicalRecordUpdate = getExistingMedicalRecord(medicalRecord.getFirstName(),
                medicalRecord.getLastName(), allMedicalRecords);

        if (MedicalRecordUpdate.isPresent()) {
            setUpdateMedicalRecord(medicalRecord, allMedicalRecords, MedicalRecordUpdate);
            jsonWrapper.setMedicalRecords(allMedicalRecords);
            jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
            return medicalRecord;
        } else {
            throw new IllegalArgumentException("Medical record does not exist");
        }
    }

    @Override
    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = jsonWrapper.getMedicalRecords();
        Optional<MedicalRecord> existingMedicalRecord = getExistingMedicalRecord(medicalRecord.getFirstName(),
                medicalRecord.getLastName(), medicalRecords);

        if (existingMedicalRecord.isPresent()) {
            medicalRecords.remove(existingMedicalRecord.get());
            jsonWrapper.setMedicalRecords(medicalRecords);
            jacksonService.saveToFile(JacksonConstants.FILE_PATH, jsonWrapper);
        } else {
            throw new IllegalArgumentException("Medical record does not exist");
        }
    }

    /**
     * Get an existing medical record from the list of medical records
     * 
     * @param firstName
     * @param lastName
     * @param medicalRecords
     * @return
     */
    private Optional<MedicalRecord> getExistingMedicalRecord(String firstName, String lastName,
            List<MedicalRecord> medicalRecords) {
        return medicalRecords.stream().filter(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName)
                && medicalRecord.getLastName().equalsIgnoreCase(lastName)).findFirst();
    }

    /**
     * Update a medical record in the list of medical records
     * 
     * @param medicalRecord
     * @param allMedicalRecords
     * @param MedicalRecordUpdate
     */
    private void setUpdateMedicalRecord(MedicalRecord medicalRecord, List<MedicalRecord> allMedicalRecords,
            Optional<MedicalRecord> MedicalRecordUpdate) {
        MedicalRecordUpdate.stream().forEach(medicalRecordUpdate -> {
            medicalRecordUpdate.setBirthdate(medicalRecord.getBirthdate());
            medicalRecordUpdate.setMedications(medicalRecord.getMedications());
            medicalRecordUpdate.setAllergies(medicalRecord.getAllergies());
        });
        allMedicalRecords.set(allMedicalRecords.indexOf(MedicalRecordUpdate.get()), MedicalRecordUpdate.get());
    }
}
