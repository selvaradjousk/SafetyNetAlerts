package com.safetynet.alerts.unittests.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.MedicalRecordMapper;

@DisplayName("MedicalRecord Mapper - Unit Tests")
class MedicalRecordMapperTest {

    private MedicalRecordMapper medicalRecordMapper = new MedicalRecordMapper();
    
	private ObjectMapper objectMapper;
	
	private MedicalRecordDTO medicalRecordDTO;
    
	private MedicalRecord medicalRecord;
	
	
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        medicalRecordDTO = MedicalRecordDTO.builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.birthDate("01/01/1960")
        		.medications(Arrays.asList("Medication 1",
        				"Medication 2",
        				"Medication 3",
        				"Medication 4"))
                .allergies(Arrays.asList("Allergy 1",
                		"Allergy 2",
                		"Allergy 3"))
                .build();
        
        medicalRecord = MedicalRecord.builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.birthDate("01/01/1960")
        		.medications(Arrays.asList("Medication 1",
        				"Medication 2",
        				"Medication 3",
        				"Medication 4"))
                .allergies(Arrays.asList("Allergy 1",
                		"Allergy 2",
                		"Allergy 3"))
                .build();
    }
    
    
	
    @Test
    @DisplayName("Model to DTO"
    		+ " - Given a MedicalRecord,"
    		+ " when requested MedicalRecord to DTO,"
    		+ " then result should confirms expected MedicalRecord DTO values")
    public void testToMedicalRecordDTO() {
    	MedicalRecordDTO result = medicalRecordMapper
    			.toMedicalRecordDTO(medicalRecord);
        assertThat(result).usingRecursiveComparison().isEqualTo(medicalRecordDTO);
    }

    
    
    @Test
    @DisplayName("Model to DTO"
    		+ " - Given a MedicalRecord with null value entry,"
    		+ " when requested MedicalRecord to DTO,"
    		+ " then result should throw NullPointerException")
    public void testToMedicalRecordDTOWithNullStationValues() {
    	assertThrows(NullPointerException.class, ()
    			-> medicalRecordMapper.toMedicalRecordDTO(null));
    }
    
    
    
    @Test
    @DisplayName("DTO to Model"
    		+ " - Given a MedicalRecordDTO,"
    		+ " when requested DTO to MedicalRecord,"
    		+ " then result should confirms expected MedicalRecord values")
    public void givenAMedicalRecordDTO_whenToMedicalRecord_thenReturnExpectedMedicalRecord() {

        MedicalRecord result = medicalRecordMapper.toMedicalRecord(medicalRecordDTO);

        assertThat(result).usingRecursiveComparison().isEqualTo(medicalRecordDTO);
    }
    
    
    

    @Test
    @DisplayName("DTO to Model"
    		+ " - Given a MedicalRecordDTO with null value entry,"
    		+ " when requested MedicalRecordDTO to MedicalRecord,"
    		+ " then result should throw NullPointerException")
    public void givenAnNullMedicalRecordDTO_whenToMedicalRecord_thenNullPointerExceptionIsThrown() {

    	assertThrows(NullPointerException.class, ()
    			-> medicalRecordMapper.toMedicalRecord(null));
    }
}

