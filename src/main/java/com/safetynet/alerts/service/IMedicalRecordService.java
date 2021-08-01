package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDTO;

/**
 * The Interface IMedicalRecordService.
 */
public interface IMedicalRecordService {

    /**
     * Adds the new medical record.
     *
     * @param newMedicalRecord the new medical record
     * @return the medical record DTO
     */
    MedicalRecordDTO addNewMedicalRecord(
    		MedicalRecordDTO newMedicalRecord);

    /**
     * Update medical record.
     *
     * @param existingMedicalRecord the existing medical record
     * @return the medical record DTO
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
     * Gets the medical record by id.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @return the medical record by id
     */
    MedicalRecordDTO getMedicalRecordById(
    		String firstName, String lastName);
}
