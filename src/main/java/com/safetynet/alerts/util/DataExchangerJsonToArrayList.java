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

@Component
public class DataExchangerJsonToArrayList {

    private final List<Person> personList = new ArrayList<>();

    private final List<FireStation> fireStationList = new ArrayList<>();

    private final List<MedicalRecord> medicalRecordList = new ArrayList<>();

    @Value("${dataSourceJsonFilePath}")
    private String dataFilePath;

    @PostConstruct
    public void readJsonDataToPOJO() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        try (InputStream fileInputStream 
        		= new FileInputStream(dataFilePath)) {
            JsonNode readJsonData 
            = mapper.readTree(fileInputStream);

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

                JsonNode medications = jsonNodeMedicalRecord.at("/medications");
                for (JsonNode jsonNodeMedications : medications) {
                    medicationsList.add(jsonNodeMedications.textValue());
                }

                JsonNode allergies = jsonNodeMedicalRecord.at("/allergies");
                for (JsonNode jsonNodeAllergies : allergies) {
                    allergiesList.add(jsonNodeAllergies.textValue());
                }

                MedicalRecord medicalRecord 
                = new MedicalRecord(firstName.asText(), lastName.asText(),
                        birthDate.asText(), medicationsList, allergiesList);

                medicalRecordList.add(medicalRecord);
            }
        }
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public List<FireStation> getFireStationList() {
        return fireStationList;
    }

    public List<MedicalRecord> getMedicalRecordList() {
        return medicalRecordList;
    }
}