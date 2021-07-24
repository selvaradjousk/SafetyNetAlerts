package com.safetynet.alerts.unittests.util;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.util.FireStationMapper;

@DisplayName("FireStation Mapper - Unit Tests")
class FireStationMapperTest {

	private FireStationMapper fireStationMapper = new FireStationMapper();

	private ObjectMapper objectMapper;
	
	private FireStationDTO fireStationDTO;
	
	private FireStation fireStation;
	
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        fireStationDTO = FireStationDTO.builder()
        		.stationId(100)
        		.address("Test address")
        		.build();
        
        fireStation = FireStation.builder()
        		.stationId(100)
        		.address("Test address")
        		.build();
    }
	
        
    @Test
    @DisplayName("Model to DTO"
    		+ " - Given a FireStation,"
    		+ " when requested FireStation to DTO,"
    		+ " then result should confirms expected FireStation DTO values")
    public void testToFireStationDTO() {
        FireStationDTO result = fireStationMapper
        		.toFireStationDTO(fireStation);
        assertThat(result).usingRecursiveComparison().isEqualTo(fireStationDTO);
    }

    
    
    @Test
    @DisplayName("Model to DTO"
    		+ " - Given a FireStation with null value entry,"
    		+ " when requested FireStation to DTO,"
    		+ " then result should throw NullPointerException")
    public void testToFireStationDTOWithNullStationValues() {
        assertThrows(NullPointerException.class, ()
        		-> fireStationMapper.toFireStationDTO(null));
    }
    
    

    @Test
    @DisplayName("DTO to Model"
    		+ " - Given a FireStationDTO,"
    		+ " when requested DTO to FireStation,"
    		+ " then result should confirms expected FireStation values")
    public void givenAFireStationDTO_whenToFireStation_thenReturnExpectedFireStation() {
        FireStation result = fireStationMapper.toFireStation(fireStationDTO);
        assertThat(result).isEqualToComparingFieldByField(fireStationDTO);
    }

    
    
    @Test
    @DisplayName("DTO to Model"
    		+ " - Given a FireStationDTO with null value entry,"
    		+ " when requested FireStationDTO to FireStation,"
    		+ " then result should throw NullPointerException")
    public void givenAnNullFireStationDTO_whenToFireStation_thenNullPointerExceptionIsThrown() {
    	assertThrows(NullPointerException.class, ()
    			-> fireStationMapper.toFireStation(null));
    }
}

