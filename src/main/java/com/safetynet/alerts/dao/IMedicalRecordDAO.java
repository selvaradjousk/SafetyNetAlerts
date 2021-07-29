package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.MedicalRecord;

/**
 * Medical Record DAO interface.
 * @author Senthil
 *
 */
public interface IMedicalRecordDAO {

	/**
	 * updates medical record.
	 * @param medicalRecord
	 * @return medicalRecordsMap
	 */
	MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * deletes medical record.
	 * @param medicalRecord
	 */
	void deleteMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * gets medical record by person id.
	 * @param firstName
	 * @param lastName
	 * @return medicalRecordsMap
	 */
	MedicalRecord getMedicalRecordByPersonId(
			String firstName, String lastName);

}
