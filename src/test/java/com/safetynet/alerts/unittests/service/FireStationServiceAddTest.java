package com.safetynet.alerts.unittests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import com.safetynet.alerts.dao.FireStationDAO;
import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.util.FireStationMapper;

@DisplayName("FIRESTATION SERVICE ADD - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class FireStationServiceAddTest {

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

    @DisplayName("Test ADD New FIRESTATION")
    @Nested
    class TestAddNewFireStation {  
    	
        @BeforeEach
        public void init() {
        	
            when(fireStationDaoMock
            		.getStationById(anyInt(), anyString()))
            .thenReturn(null);
            
            when(fireStationDaoMock
            		.updateStation(any(FireStation.class)))
            .thenReturn(testFireStation1);
        }
        
        @Test
        @DisplayName("Check <NotNull>"
        		+ " - Given a FireStation,"
        		+ " when Add FireStation,"
        		+ " then FireStation should be saved")
        public void testAddFireStationNotNull() {
        	
            FireStationDTO fireCreated = fireStationService
            		.addNewFireStation(fireStationDTO);

            assertNotNull(fireCreated);
        }
        
        @Test
        @DisplayName("Check <Validate> match of both same record instance>"
        		+ " - Given a FireStation,"
        		+ " when Add FireStation,"
        		+ " then FireStation should be saved")
        public void testAddFireStationMatchTestValueCheck() {
        	
            FireStationDTO fireCreated = fireStationService
            		.addNewFireStation(fireStationDTO);

            assertThat(fireCreated).usingRecursiveComparison().isEqualTo(fireStationDTO);
        }
        
        @Test
        @DisplayName("Check <Execution Order>"
        		+ " - Given a FireStation,"
        		+ " when Add FireStation,"
        		+ " then FireStation should be saved")
        public void testAddFireStationExecutionOrder() {
        	
            fireStationService
            		.addNewFireStation(fireStationDTO);

            InOrder inOrder = inOrder(fireStationDaoMock);
            inOrder.verify(fireStationDaoMock).getStationById(anyInt(), anyString());
            inOrder.verify(fireStationDaoMock).updateStation(any(FireStation.class));
        }
        }

    @Test
    @DisplayName("ADD FIRESTATION ERROR on Existing FireStation"
    		+ " - Given a existing fireStation,"
    		+ " when Add fireStation,"
    		+ " then throw DataAlreadyRegisteredException")
    public void testAddFireStationThatExistAlready() {
        when(fireStationDaoMock
        		.getStationById(anyInt(), anyString()))
        .thenReturn(testFireStation1);

        assertThrows(DataAlreadyRegisteredException.class, ()
        		-> fireStationService.addNewFireStation(fireStationDTO));
    }    
}
