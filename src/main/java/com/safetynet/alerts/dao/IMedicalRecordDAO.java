package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;

public interface IMedicalRecordDAO {

	MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

	void deleteMedicalRecord(MedicalRecord medicalRecord);

	MedicalRecord getMedicalRecordByPersonId(String firstName, String lastName);
	
}