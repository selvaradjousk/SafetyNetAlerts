package com.safetynet.alerts.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

/**
 * Data file reader class.
 * @author Senthil
 *
 */
@Component
public class DataFileReader {

    /**
     * person list.
     */
    private final List<Person> personList = new ArrayList<>();

    /**
     * fire station list.
     */
    private final List<FireStation> fireStationList = new ArrayList<>();

    /**
     * medical record list.
     */
    private final List<MedicalRecord> medicalRecordList = new ArrayList<>();

    /**
     * data file path.
     */
    @Value("${dataSourceJsonFilePath}")
    private String dataFilePath;

    /**
     * Read json data to DO.
     * @throws IOException
     */
    @PostConstruct
    public void readJsonDataToPOJO() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        try (InputStream fileInputStream
        		= new FileInputStream(dataFilePath)) {
            JsonNode readJsonData
            = mapper.readTree(fileInputStream);

            readPersonData(readJsonData);

            readFireStationData(readJsonData);

            readMedicalRecordData(readJsonData);
        }
    }

	/**
	 * Read MedicalRecord Data.
	 * @param readJsonData
	 */
	private void readMedicalRecordData(final JsonNode readJsonData) {
		// Transfers the all medical record details
		// in the corresponding array list
		JsonNode medicalRecords
		= readJsonData.at("/medicalrecords");
		for (JsonNode jsonNodeMedicalRecord : medicalRecords) {
		    JsonNode lastName = jsonNodeMedicalRecord.at("/lastName");
		    JsonNode firstName = jsonNodeMedicalRecord.at("/firstName");
		    JsonNode birthDate = jsonNodeMedicalRecord.at("/birthdate");

		    List<String> medicationsList = new ArrayList<>();
		    List<String> allergiesList = new ArrayList<>();

		    readMedicationData(jsonNodeMedicalRecord, medicationsList);

		    readAllergiesData(jsonNodeMedicalRecord, allergiesList);

		    MedicalRecord medicalRecord
		    = new MedicalRecord(
		    		firstName.asText(),
		    		lastName.asText(),
		            birthDate.asText(),
		            medicationsList,
		            allergiesList);

		    medicalRecordList.add(medicalRecord);
		}
	}

	/**
	 * Read Medication Data.
	 * @param jsonNodeMedicalRecord
	 * @param medicationsList
	 */
	private void readMedicationData(
			final JsonNode jsonNodeMedicalRecord,
			final List<String> medicationsList) {

		JsonNode medications = jsonNodeMedicalRecord.at("/medications");
		for (JsonNode jsonNodeMedications : medications) {
		    medicationsList.add(jsonNodeMedications.textValue());
		}
	}

	/**
	 * Read Allergies Data.
	 * @param jsonNodeMedicalRecord
	 * @param allergiesList
	 */
	private void readAllergiesData(
			final JsonNode jsonNodeMedicalRecord,
			final List<String> allergiesList) {

		JsonNode allergies = jsonNodeMedicalRecord.at("/allergies");
		for (JsonNode jsonNodeAllergies : allergies) {
		    allergiesList.add(jsonNodeAllergies.textValue());
		}
	}

	/**
	 * Read FireStation Data.
	 * @param readJsonData
	 */
	private void readFireStationData(final JsonNode readJsonData) {
		// Transfers the all Firestation details
		// in the corresponding array list
		JsonNode fireStations = readJsonData
				.at("/firestations");
		for (JsonNode jsonNodeFirestation : fireStations) {
		    FireStation firestation
		    = new FireStation(
		    		jsonNodeFirestation.get("address").asText(),
		    		jsonNodeFirestation.get("station").asInt());

		    fireStationList.add(firestation);
		}
	}

	/**
	 * Read Person Data.
	 * @param readJsonData
	 */
	private void readPersonData(final JsonNode readJsonData) {
		// Transfers the all person details
		// in the corresponding array list
		JsonNode persons = readJsonData
				.at("/persons");
		for (JsonNode jsonNodePerson : persons) {
		    Person person = new Person(
		    		jsonNodePerson.get("firstName").asText(),
		            jsonNodePerson.get("lastName").asText(),
		            jsonNodePerson.get("address").asText(),
		            jsonNodePerson.get("city").asText(),
		            jsonNodePerson.get("zip").asInt(),
		            jsonNodePerson.get("phone").asText(),
		            jsonNodePerson.get("email").asText());

		    personList.add(person);
		}
	}

    /**
     * Get Person List.
     * @return person list
     */
    public List<Person> getPersonList() {
        return personList;
    }

    /**
     * Get FireStation List.
     * @return fire station list
     */
    public List<FireStation> getFireStationList() {
        return fireStationList;
    }

    /**
     * Get Medical Record List.
     * @return medical record list
     */
    public List<MedicalRecord> getMedicalRecordList() {
        return medicalRecordList;
    }
}
