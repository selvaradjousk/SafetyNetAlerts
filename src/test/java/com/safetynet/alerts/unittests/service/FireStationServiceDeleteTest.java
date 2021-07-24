package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
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

@DisplayName("FIRESTATION SERVICE DELETE - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class FireStationServiceDeleteTest {

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

    private static ObjectMapper objectMapper;
	
	private static List<FireStation> fireStations;

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

    // ***********************************************************************************
    
    @Test
    @DisplayName("Check (existing firestation)"
    		+ " - Given a existing fireStation,"
    		+ " when request delete fireStation,"
    		+ " then delete fireStation")
		public void testDeleteFireStation() {
        when(fireStationDaoMock
        		.getStationById(anyInt(), anyString()))
        .thenReturn(testFireStation1);

        fireStationService.deleteExistingStation(testFireStation1.getStationId(), testFireStation1.getAddress());

        InOrder inOrder = inOrder(fireStationDaoMock);
        inOrder.verify(fireStationDaoMock).getStationById(anyInt(), anyString());
        inOrder.verify(fireStationDaoMock).deleteStationByMapping(any(FireStation.class));
    }
    
    
    @Test
    @DisplayName("Check (non existing FireStation Exception Thrown"
    		+ " - Given a non existing fireStation,"
    		+ " when request delete fireStation,"
    		+ " then throw DataNotFoundException")
		public void testDeleteFireStationFoeNonExistingMedicalRecord() {
            when(fireStationDaoMock
            		.getStationById(anyInt(), anyString()))
            .thenReturn(null);

            assertThrows(DataNotFoundException.class, ()
            		-> fireStationService.deleteExistingStation(testFireStation1.getStationId(), testFireStation1.getAddress()));
    }
}
