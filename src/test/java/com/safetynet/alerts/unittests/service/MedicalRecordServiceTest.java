package com.safetynet.alerts.unittests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
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


@DisplayName("Medical Record Service - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

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
	
	MedicalRecordDTO medicalRecordAdded, medicalRecordUpdated,
	medicalRecordByIdFound;
    
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
    

    // ***********************************************************************************

    @DisplayName("Test ADD MEDICAL RECORD")
    @Nested
    class TestAddNewMedicalRecord {
    	
        @BeforeEach
        public void init() {
            when(medicalRecordDAOMock
            		.getMedicalRecordByPersonId(anyString(), anyString()))
            .thenReturn(null);
            
            when(medicalRecordMapper
            		.toMedicalRecord(any(MedicalRecordDTO.class)))
            .thenReturn(medicalRecord);
           
            when(medicalRecordDAOMock
            		.updateMedicalRecord(any(MedicalRecord.class)))
            .thenReturn(medicalRecord);
            
            when(medicalRecordMapper
            		.toMedicalRecordDTO(any(MedicalRecord.class)))
            .thenReturn(medicalRecordDTO);

        }
        
   
        @Test
        @DisplayName("Check <Execution Order >"
        		+ " - Given a new medicalRecord,"
        		+ " when add Medical Record,"
        		+ " then all steps are executed in"
        		+ " correct order and number of expected times")
        public void testAddMedicalRecordExecutionOrderCheck() {

        	medicalRecordService
            		.addNewMedicalRecord(medicalRecordDTO);

            InOrder inOrder = inOrder(medicalRecordDAOMock, medicalRecordMapper, medicalRecordDAOMock, medicalRecordMapper);
            inOrder.verify(medicalRecordDAOMock).getMedicalRecordByPersonId(anyString(), anyString());
            inOrder.verify(medicalRecordMapper).toMedicalRecord(any(MedicalRecordDTO.class));
            inOrder.verify(medicalRecordDAOMock).updateMedicalRecord(any(MedicalRecord.class));
            inOrder.verify(medicalRecordMapper).toMedicalRecordDTO(any(MedicalRecord.class));
            
            verify(medicalRecordDAOMock, times(1)).getMedicalRecordByPersonId(anyString(), anyString());
            verify(medicalRecordMapper, times(1)).toMedicalRecord(any(MedicalRecordDTO.class));
            verify(medicalRecordDAOMock, times(1)).updateMedicalRecord(any(MedicalRecord.class));
            verify(medicalRecordMapper, times(1)).toMedicalRecordDTO(any(MedicalRecord.class));

        }
        
        
        @Test
        @DisplayName("Check <Not Null> on Add Record"
        		+ " - Given a new medicalRecord,"
        		+ " when add Medical Record,"
        		+ " then medicalRecord is not null")
        public void testAddMedicalRecordCheckNotNull() {

            medicalRecordAdded = medicalRecordService
            		.addNewMedicalRecord(medicalRecordDTO);
            assertNotNull(medicalRecordAdded);
        }
        
        
        @Test
        @DisplayName("Check <Validate> match of both same record instance"
        		+ " - Given a new medicalRecord,"
        		+ " when add Medical Record,"
        		+ " then medicalRecord should be added and same as test record")
        public void testAddMedicalRecord() {

            medicalRecordAdded = medicalRecordService
            		.addNewMedicalRecord(medicalRecordDTO);

            assertThat(medicalRecordAdded).usingRecursiveComparison().isEqualTo(medicalRecordDTO);
        }
      } 
    
    
    @Test
    @DisplayName("ERROR ADD MEDICAL RECORD - Duplication"
    		+ " - Given a existing medicalRecord,"
    		+ " when Add MedicalRecord,"
    		+ " then throw DataAlreadyRegisteredException")
    public void testAddMedicalRecordThatExistAlready() {
        when(medicalRecordDAOMock
        		.getMedicalRecordByPersonId(anyString(), anyString()))
        .thenReturn(medicalRecord);

        assertThrows(DataAlreadyRegisteredException.class, ()
        		-> medicalRecordService.addNewMedicalRecord(medicalRecordDTO));
    }
    
    // ***********************************************************************************

    @DisplayName("Test UPDATE MEDICAL RECORD")
    @Nested
    class TestUpdateMedicalRecord {
    	
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
        		+ " - Given a existing medicalRecord,"
        		+ " when request update MedicalRecord,"
        		+ " then all steps are executed in correct order and number of expected times")
        public void testUpdateMedicalRecordExecutionOrderCheck() {


            medicalRecordService
            		.updateMedicalRecord(medicalRecordDTO);


            InOrder inOrder = inOrder(medicalRecordDAOMock, medicalRecordMapper);
            inOrder.verify(medicalRecordDAOMock).getMedicalRecordByPersonId(anyString(), anyString());
            inOrder.verify(medicalRecordMapper).toMedicalRecordDTO(any(MedicalRecord.class));
            
            verify(medicalRecordDAOMock, times(1)).getMedicalRecordByPersonId(anyString(), anyString());
            verify(medicalRecordMapper, times(1)).toMedicalRecordDTO(any(MedicalRecord.class));
        }
        
        @Test
        @DisplayName("Check <Not Null> Record Exists on Update Record"
        		+ " - Given a existing medicalRecord,"
        		+ " when request update MedicalRecord,"
        		+ " then medicalRecord should not be null")
        public void testUpdateMedicalRecordCheckRecordNotNull() {

            medicalRecordUpdated = medicalRecordService
            		.updateMedicalRecord(medicalRecordDTO);

            assertNotNull((medicalRecordUpdated.getAllergies()).contains("some allergies1"));
        }
        
        @Test
        @DisplayName("Check <Validate> match of updated values"
        		+ " - Given a existing medicalRecord,"
        		+ " when request update MedicalRecord,"
        		+ " then medicalRecord should be updated")
        public void testUpdateMedicalRecord() {

           medicalRecordUpdated = medicalRecordService
            		.updateMedicalRecord(medicalRecordDTO);

            assertTrue((medicalRecordUpdated.getAllergies()).contains("some allergies1"));
        }

      }


        @Test
        @DisplayName("ERROR UPDATE MEDICAL RECORD on Non Existing Record"
        		+ " - Given a non existing medicalRecord,"
        		+ " when request update MedicalRecord,"
        		+ " then medicalRecord should throw DataNotFoundException")
    		public void testUpdateMedicalRecordForRecordDoesNotExist() {
            when(medicalRecordDAOMock
            		.getMedicalRecordByPersonId(anyString(), anyString()))
            .thenReturn(null);

            assertThrows(DataNotFoundException.class, ()
            		-> medicalRecordService.updateMedicalRecord(medicalRecordDTO));
        }
    
    // ***********************************************************************************

    @DisplayName("Test DELETE MEDICAL RECORD")
    @Nested
    class TestDeleteMedicalRecord {

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
}







