package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.MedicalRecordDAO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.util.MedicalRecordMapper;


@DisplayName("MEDICAL RECORD SERVICE DELETE - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceDeleteTest {

    @Mock
    private MedicalRecordDAO medicalRecordDAOMock;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Mock
    private static MedicalRecord medicalRecord;

    @Mock
    private static MedicalRecordDTO medicalRecordDTO;

	private ObjectMapper objectMapper;
	
   
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        medicalRecordDTO = MedicalRecordDTO.builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.birthDate("01/01/1960")
        		.medications(Arrays.asList(
        				"some medication1",
        				"some medication1",
        				"some medication1"))
                .allergies(Arrays.asList(
                		"some allergies1",
                		"some allergies2",
                		"some allergies3"))
                .build();
        
        medicalRecord = MedicalRecord.builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.birthDate("01/01/1960")
        		.medications(Arrays.asList(
        				"some medication1",
        				"some medication1",
        				"some medication1"))
                .allergies(Arrays.asList(
                		"some allergies1",
                		"some allergies2",
                		"some allergies3"))
                .build();
    }
    

 
    // ***********************************************************************************


    @Test
    @DisplayName("Check (for existing input)"
    		+ " - Given a existing medicalRecord,"
    		+ " when request delete MedicalRecord,"
    		+ " then delete MedicalRecord")
		public void testDeleteMedicalRecord() {
        when(medicalRecordDAOMock
        		.getMedicalRecordByPersonId(anyString(), anyString()))
        .thenReturn(medicalRecord);

        medicalRecordService.deleteMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName());

        InOrder inOrder = inOrder(medicalRecordDAOMock);
        inOrder.verify(medicalRecordDAOMock).getMedicalRecordByPersonId(anyString(), anyString());
        inOrder.verify(medicalRecordDAOMock).deleteMedicalRecord(any(MedicalRecord.class));
    }
   
	@Test
    @DisplayName("Check (for non existing input)"
    		+ " - Given a non existing medicalRecord,"
    		+ " when request delete MedicalRecord,"
    		+ " then throw DataNotFoundException")
		public void testDeleteMedicalRecordFoeNonExistingMedicalRecord() {
            when(medicalRecordDAOMock
            		.getMedicalRecordByPersonId(anyString(), anyString()))
            .thenReturn(null);

            assertThrows(DataNotFoundException.class, ()
        		-> medicalRecordService.deleteMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName()));
    }
}







