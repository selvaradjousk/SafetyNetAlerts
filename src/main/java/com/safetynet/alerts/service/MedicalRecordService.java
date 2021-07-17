package com.safetynet.alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.IMedicalRecordDAO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.MedicalRecordMapper;

@Service
public class MedicalRecordService implements IMedicalRecordService {


    private final IMedicalRecordDAO medicalRecordDAO;

    private final MedicalRecordMapper medicalRecordMapper;

    @Autowired
    public MedicalRecordService(
    		final IMedicalRecordDAO medicalRecordDAO,
    		final MedicalRecordMapper
    		medicalRecordMapper) {
        this.medicalRecordDAO = medicalRecordDAO;
        this.medicalRecordMapper = medicalRecordMapper;
    }



     public MedicalRecordDTO getMedicalRecordById(
    		 final String firstName,
    		 final String lastName) {
        MedicalRecord medicalRecord = medicalRecordDAO
        		.getMedicalRecordByPersonId(firstName, lastName);

        if (medicalRecord == null) {
            throw new DataNotFoundException( firstName + " " + lastName
            		+ ": Not able to retrieve this medicalRecord.");
        }

        return medicalRecordMapper.toMedicalRecordDTO(medicalRecord);
    }
	
	@Override
	public MedicalRecordDTO addNewMedicalRecord(MedicalRecordDTO newMedicalRecord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO existingMedicalRecord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMedicalRecord(String firstName, String lastName) {
		// TODO Auto-generated method stub
		
	}
}
