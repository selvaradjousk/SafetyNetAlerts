package com.safetynet.alerts.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.DataFileReader;

@Repository
public class MedicalRecordDAO implements IMedicalRecordDAO {

	
	private final Map<String, MedicalRecord> medicalRecordsMap = new HashMap<>();
	
    @Autowired
    public MedicalRecordDAO(final DataFileReader jsonDataArrayList) {
                jsonDataArrayList.getMedicalRecordList().forEach(medicalRecord -> medicalRecordsMap.put(medicalRecord.getFirstName()
                + medicalRecord.getLastName(), medicalRecord));
    }
    
    

	public MedicalRecord getMedicalRecordByPersonId(final String firstName, String lastName) {
            return medicalRecordsMap.get(firstName + lastName);
    }
	
	
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		// TODO Auto-generated method stub
		return null;
	}


	public void deleteMedicalRecord(MedicalRecord medicalRecord) {
		// TODO Auto-generated method stub
		
	}
	
}
