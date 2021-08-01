package com.safetynet.alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.IMedicalRecordDAO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.MedicalRecordMapper;

/**
 * MedicalRecord Service class.
 * @author Senthil
 *
 */
@Service
public class MedicalRecordService implements IMedicalRecordService {

    /**
     * IMedicalRecordDAO instance.
     */
    private final IMedicalRecordDAO medicalRecordDAO;

    /**
     * MedicalRecordMapper instance.
     */
    private final MedicalRecordMapper medicalRecordMapper;

    /**
     * Class Constructor.
     *
     * @param medicalEndpointRecordDAO the medical endpoint record DAO
     * @param medicalRecordEndpointMapper the medical record endpoint mapper
     */
    @Autowired
    public MedicalRecordService(
    		final IMedicalRecordDAO medicalEndpointRecordDAO,
    		final MedicalRecordMapper
    		medicalRecordEndpointMapper) {
        this.medicalRecordDAO = medicalEndpointRecordDAO;
        this.medicalRecordMapper = medicalRecordEndpointMapper;
    }


     /**
      * Gets the medical record by id.
      *
      * @param firstName the first name
      * @param lastName the last name
      * @return the medical record by id
      */
     public MedicalRecordDTO getMedicalRecordById(
    		 final String firstName,
    		 final String lastName) {
        MedicalRecord medicalRecord = medicalRecordDAO
        		.getMedicalRecordByPersonId(firstName, lastName);

        if (medicalRecord == null) {
            throw new DataNotFoundException(firstName + " " + lastName
            		+ ": Not able to retrieve this medicalRecord.");
        }

        return medicalRecordMapper
        		.toMedicalRecordDTO(medicalRecord);
    }

     /**
      * Adds new medical Record.
      *
      * @param medicalRecord the medical record
      * @return medicalRecordMapper.toMedicalRecordDTO(medicalRecordSaved)
      */
     public MedicalRecordDTO addNewMedicalRecord(
    		 final MedicalRecordDTO medicalRecord) {
         MedicalRecord medicalRecordFound = medicalRecordDAO
        		 .getMedicalRecordByPersonId(
        				 medicalRecord.getFirstName(),
        				 medicalRecord.getLastName());

        if (medicalRecordFound != null) {
            throw new DataAlreadyRegisteredException(
            		"MedicalRecord already registered");
        }
        MedicalRecord medicalRecordToSave = medicalRecordMapper
        		.toMedicalRecord(medicalRecord);
        MedicalRecord medicalRecordSaved = medicalRecordDAO
        		.updateMedicalRecord(medicalRecordToSave);

        return medicalRecordMapper
        		.toMedicalRecordDTO(medicalRecordSaved);
    }



    /**
     * Update medical record.
     *
     * @param medicalRecord the medical record
     * @return the medical record DTO
     */
    public MedicalRecordDTO updateMedicalRecord(
    		final MedicalRecordDTO medicalRecord) {
        MedicalRecord medicalRecordFound = medicalRecordDAO
        		.getMedicalRecordByPersonId(
        				medicalRecord.getFirstName(),
        				medicalRecord.getLastName());

        if (medicalRecordFound == null) {
            throw new DataNotFoundException("MedicalRecord not found");
        }
        medicalRecordFound.setBirthDate(medicalRecord
        		.getBirthDate());
        medicalRecordFound.setMedications(medicalRecord
        		.getMedications());
        medicalRecordFound.setAllergies(medicalRecord
        		.getAllergies());

        return medicalRecordMapper
        		.toMedicalRecordDTO(medicalRecordFound);
    }

    /**
     * Delete medical Record.
     *
     * @param firstName the first name
     * @param lastName the last name
     */
    public void deleteMedicalRecord(
    		final String firstName,
    		final String lastName) {
        MedicalRecord medicalRecordToDelete = medicalRecordDAO
        		.getMedicalRecordByPersonId(firstName, lastName);

        if (medicalRecordToDelete == null) {
            throw new DataNotFoundException("MedicalRecord not found");
        }

        medicalRecordDAO.deleteMedicalRecord(medicalRecordToDelete);
    }
}
