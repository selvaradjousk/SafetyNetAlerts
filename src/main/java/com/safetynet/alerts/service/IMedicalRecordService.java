package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDTO;

/**
 * The Interface IMedicalRecordService.
 */
public interface IMedicalRecordService {

    /**
     * Adds new medical Record.
     *
     * @param newMedicalRecord the new medical record
     * @return medicalRecordMapper.toMedicalRecordDTO(medicalRecordSaved)
     */
    MedicalRecordDTO addNewMedicalRecord(
    		MedicalRecordDTO newMedicalRecord);

    /**
     * updates medical Record.
     *
     * @param existingMedicalRecord the existing medical record
     * @return medicalRecordMapper.toMedicalRecordDTO(medicalRecordFound)
     */
    MedicalRecordDTO updateMedicalRecord(
    		MedicalRecordDTO existingMedicalRecord);

    /**
     * Delete medical Record.
     *
     * @param firstName the first name
     * @param lastName the last name
     */
    void deleteMedicalRecord(
    		String firstName, String lastName);

    /**
     * get medical Record by id.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @return medicalRecordMapper.toMedicalRecordDTO(medicalRecord)
     */
    MedicalRecordDTO getMedicalRecordById(
    		String firstName, String lastName);
}
