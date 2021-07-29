package com.safetynet.alerts.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.DataFileReader;

/**
 * The Class MedicalRecordDAO.
 * @author Senthil
 */
@Repository
public class MedicalRecordDAO implements IMedicalRecordDAO {

	/** The medical records map. */
	private final Map<String, MedicalRecord> medicalRecordsMap
	= new HashMap<>();

	/**
	 * Instantiates a new medical record DAO.
	 *
	 * @param jsonDataArrayList the json data array list
	 */
	@Autowired
	public MedicalRecordDAO(final DataFileReader jsonDataArrayList) {

		jsonDataArrayList
		.getMedicalRecordList()
		.forEach(medicalRecord
				-> medicalRecordsMap
				.put(
						medicalRecord.getFirstName()
						+ medicalRecord.getLastName(),
						medicalRecord));
	}

	/**
	 * gets medical record by person id.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the medical record by person id
	 */
	public MedicalRecord getMedicalRecordByPersonId(
			final String firstName,
			final String lastName) {

		return medicalRecordsMap
				.get(firstName + lastName);
	}

	/**
	 * updates medical record.
	 *
	 * @param medicalRecord the medical record
	 * @return the medical record
	 */
	public MedicalRecord updateMedicalRecord(
			final MedicalRecord medicalRecord) {

		medicalRecordsMap
		.put(
				medicalRecord.getFirstName()
				+ medicalRecord.getLastName(),
				medicalRecord);

		return medicalRecordsMap
				.get(
						medicalRecord.getFirstName()
						+ medicalRecord.getLastName());
	}

	/**
	 * deletes medical record.
	 *
	 * @param medicalRecord the medical record
	 */
	public void deleteMedicalRecord(
			final MedicalRecord medicalRecord) {
        medicalRecordsMap.remove(
        		medicalRecord.getFirstName()
        		+ medicalRecord.getLastName());
	}
}
