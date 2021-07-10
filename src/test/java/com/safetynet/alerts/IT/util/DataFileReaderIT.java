package com.safetynet.alerts.IT.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.DataFileReader;

@DisplayName("IT - Reading JsonFile To ArrayList")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataFileReaderIT {

    @Autowired
    private DataFileReader dataFileReader;

    @DisplayName("ReadingJsonInputFile for PERSON"
    		+ " - Given input Json File to read"
    		+ "When get Person List"
    		+ "then return expected number of entries in the list")
    @Test
    public void testgetPersonListFromJsonSourceFile() {
        List<Person> listOfPersons = dataFileReader.getPersonList();

        assertNotNull(listOfPersons);
        assertEquals(23, listOfPersons.size());
    }
    
    @DisplayName("ReadingJsonInputFile for Firestation"
    		+ " - Given input Json File to read"
    		+ "When get FireStation List"
    		+ "then return expected number of entries in the list")
    @Test
    public void testgetFirestationListFromJsonSourceFile() {
        List<FireStation> listOfFireStations = dataFileReader.getFireStationList();

        assertNotNull(listOfFireStations);
        assertEquals(13, listOfFireStations.size());
    }

    @DisplayName("ReadingJsonInputFile for MedicalRecord"
    		+ " - Given input Json File to read"
    		+ "When get MedicalRecord List"
    		+ "then return expected number of entries in the list")
    @Test
    public void testgetMedicalRecordListFromJsonSourceFile() {
        List<MedicalRecord> listOfMedicalRecords = dataFileReader.getMedicalRecordList();

        assertNotNull(listOfMedicalRecords);
        assertEquals(23, listOfMedicalRecords.size());
    }

}
