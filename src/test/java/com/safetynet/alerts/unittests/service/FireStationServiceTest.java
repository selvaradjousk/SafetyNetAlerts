package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.FireStationDAO;
import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.util.FireStationMapper;

@DisplayName("FireStation Service - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    private FireStationDAO fireStationDaoMock;
    
    @Mock
    private FireStationMapper fireStationMapper;

    @InjectMocks
    private FireStationService fireStationService;

    @Mock
    private static FireStationDTO fireStationDTO;

    private static FireStation testFireStation1;

    private static FireStation testFireStation2;

	private ObjectMapper objectMapper;
	
    List<FireStation> fireStations;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        fireStationDTO = FireStationDTO.builder()
        		.stationId(100)
        		.address("Test address")
        		.build();
        
        testFireStation1 = FireStation.builder()
        		.stationId(100)
        		.address("Test address")
        		.build();
        
        testFireStation2 = FireStation.builder()
        		.stationId(200)
        		.address("Test2 address")
        		.build();
        
        fireStations = Arrays.asList(testFireStation1, testFireStation2);
    }

     @Test
    @DisplayName("GET FIRESTATION BY ID"
    		+ " - Given a fireStation WITH PERSON ID,"
    		+ " when request get fireStation,"
    		+ " then return fireStation")
    public void testGetFireStationById() {
            when(fireStationDaoMock
            		.getStationById(anyString(), anyInt()))
            .thenReturn(testFireStation1);

        FireStationDTO fireByIdFound = fireStationService
        		.getFireStationById(testFireStation1.getAddress(), testFireStation1.getStationId());

        assertEquals(fireStationDTO.getStationId(), fireByIdFound.getStationId());
    }

     @Test
     @DisplayName("GET FIRESTATION BY ID (No valid id)"
     		+ " - Given a fireStation with ID with no id,"
     		+ " when request get fireStation,"
     		+ " then return throws DataNotFoundException")
     public void testGetFireStationByIdforInvalidId() {
         when(fireStationDaoMock
         		.getStationById(anyString(), anyInt()))
         .thenReturn(null);

         assertThrows(DataNotFoundException.class, ()
         		->  fireStationService.getFireStationById(testFireStation1.getAddress(), testFireStation1.getStationId()));
     }
     
}
