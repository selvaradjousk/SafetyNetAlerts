package com.safetynet.alerts.unittests.dao;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dao.IMedicalRecordDAO;
import com.safetynet.alerts.dao.MedicalRecordDAO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.DataFileReader;

@DisplayName("Medical Record DAO - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class MedicalRecordDAOTest {

    private IMedicalRecordDAO iMedicalRecordDAO;

    @Mock
    private DataFileReader dataFileReader;

    private static MedicalRecord medicalRecord1, medicalRecord2,
    medicalRecord3, medicalRecordFound, medicalRecordSaved;
    
    @BeforeEach
    public void setUp() {
    	
    	medicalRecord1 = new MedicalRecord(
    			"Test1 FirstName",
    			"Test1 LastName",
    			"01/01/1976",
                Arrays.asList("medications 1"),
                Arrays.asList("alergies 1"));
    	
        medicalRecord2 = new MedicalRecord(
        		"Test2 FirstName",
        		"Test2 LastName",
        		"01/01/2005",
        		Arrays.asList("medication2-1"),
        		Arrays.asList("allergies2-1"));

        when(dataFileReader
        		.getMedicalRecordList())
        .thenReturn(Arrays.asList(medicalRecord1, medicalRecord2));

        iMedicalRecordDAO = new MedicalRecordDAO(dataFileReader);
    }
    
    
    @Test
    @DisplayName("Check (valid id )"
    		+ "Given a person Id,"
    		+ " when getMedicalRecordByPersonId,"
    		+ " then medicalRecord returned")
    public void testGetMedicalRecordByPersonIdForValidInput() {
        medicalRecordFound = iMedicalRecordDAO
        		.getMedicalRecordByPersonId(
        				"Test1 FirstName",
        				"Test1 LastName");

        assertEquals(medicalRecord1, medicalRecordFound);
    }
 
    
    
}