package com.safetynet.alerts.unittests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.MedicalRecordDAO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.util.MedicalRecordMapper;


@DisplayName("MEDICAL RECORD SERVICE GET - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceGetTest {

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
	
	MedicalRecordDTO medicalRecordByIdFound;
    
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
    @DisplayName("Test GET MEDICAL RECORD")
    @Nested
    class TestGetMedicalRecord {
    	
        @BeforeEach
        public void init() {
            when(medicalRecordDAOMock
            		.getMedicalRecordByPersonId(anyString(), anyString()))
            .thenReturn(medicalRecord);
            when(medicalRecordMapper
            		.toMedicalRecordDTO(any(MedicalRecord.class)))
            .thenReturn(medicalRecordDTO);
        }	
	
    @Test
    @DisplayName("Check <Execution Order > and times"
    		+ " - Given a medicalRecord WITH PERSON ID,"
    		+ " when request get MedicalRecord,"
    		+ " then all steps are executed in"
    		+ " correct order and number of expected times")
    public void testGetMedicalRecordByIdExecutionOrderCheck() {

    	medicalRecordService
        		.getMedicalRecordById(
        				medicalRecordDTO.getFirstName(),
        				medicalRecordDTO.getLastName());

        InOrder inOrder = inOrder(medicalRecordDAOMock, medicalRecordMapper);
        inOrder.verify(medicalRecordDAOMock).getMedicalRecordByPersonId(anyString(), anyString());
        inOrder.verify(medicalRecordMapper).toMedicalRecordDTO(any(MedicalRecord.class));
        
        verify(medicalRecordDAOMock, times(1)).getMedicalRecordByPersonId(anyString(), anyString());
        verify(medicalRecordMapper, times(1)).toMedicalRecordDTO(any(MedicalRecord.class));
    }
    
    
    @Test
    @DisplayName("Check <Not Null> on Get Record"
    		+ " - Given a medicalRecord WITH PERSON ID,"
    		+ " when request get MedicalRecord,"
    		+ " then returned medical record not null")
    public void testGetMedicalRecordByIdNotNullCheck() {

        medicalRecordByIdFound = medicalRecordService
        		.getMedicalRecordById(
        				medicalRecordDTO.getFirstName(),
        				medicalRecordDTO.getLastName());

        assertNotNull(medicalRecordByIdFound);
    }
    
    
    @Test
    @DisplayName("Check <Validate> match of both same record test Value"
    		+ " - Given a medicalRecord WITH PERSON ID,"
    		+ " when request get MedicalRecord,"
    		+ " then return medical record with the same test value")
    public void testGetMedicalRecordByIdValidatedByTestValue() {

        medicalRecordByIdFound = medicalRecordService
        		.getMedicalRecordById(
        				medicalRecordDTO.getFirstName(),
        				medicalRecordDTO.getLastName());

        assertEquals(medicalRecordDTO, medicalRecordByIdFound);
    }
    
  }
    
    @Test
    @DisplayName("GET MEDICAL RECORD invalid id Exception"
    		+ " - Given a medicalRecord with person ID with no id,"
    		+ " when request get MedicalRecord,"
    		+ " then return throws DataNotFoundException")
    public void testGetMedicalRecordByIdforInvalidId() {
        when(medicalRecordDAOMock
        		.getMedicalRecordByPersonId(anyString(), anyString()))
        .thenReturn(null);

        assertThrows(DataNotFoundException.class, ()
        		->  medicalRecordService.getMedicalRecordById(anyString(), anyString()));
    }
}







