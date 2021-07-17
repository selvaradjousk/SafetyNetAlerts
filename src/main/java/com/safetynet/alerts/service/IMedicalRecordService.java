package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDTO;

public interface IMedicalRecordService {

    MedicalRecordDTO addNewMedicalRecord(MedicalRecordDTO newMedicalRecord);

    MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO existingMedicalRecord);

    void deleteMedicalRecord(String firstName, String lastName);

    MedicalRecordDTO getMedicalRecordById(String firstName, String lastName);
}
