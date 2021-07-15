package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
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
import com.safetynet.alerts.model.Person;
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

    // ***********************************************************************************

    @DisplayName("Test GET FIRESTATION BY ID")
    @Nested
    class TestGetFireStationById {  
    	
        @BeforeEach
        public void init() {
          }

     @Test
    @DisplayName("Check (VALID ID)"
    		+ " - Given a fireStation WITH ID,"
    		+ " when request get fireStation,"
    		+ " then return fireStation")
    public void testGetFireStationById() {
            when(fireStationDaoMock
            		.getStationById(anyInt(), anyString()))
            .thenReturn(testFireStation1);

        FireStationDTO fireByIdFound = fireStationService
        		.getFireStationById(testFireStation1.getStationId(), testFireStation1.getAddress());

        assertEquals(fireStationDTO.getStationId(), fireByIdFound.getStationId());
    }

     @Test
     @DisplayName("Check (No valid id input)"
     		+ " - Given a fireStation with ID with no id,"
     		+ " when request get fireStation,"
     		+ " then return throws DataNotFoundException")
     public void testGetFireStationByIdforInvalidId() {
         when(fireStationDaoMock
         		.getStationById(anyInt(), anyString()))
         .thenReturn(null);

         assertThrows(DataNotFoundException.class, ()
         		->  fireStationService.getFireStationById(testFireStation1.getStationId(), testFireStation1.getAddress()));
     }
     
     // ***********************************************************************
    }  

    @DisplayName("Test GET AddressByFIRESTATION BY ID")
    @Nested
    class TestGetAddressByStationId {  
    	
        @BeforeEach
        public void init() {
          }
     
     @Test
     @DisplayName("Check (Address by FireStation)"
     		+ " - Given a fireStation,"
     		+ " when request get Address,"
     		+ " then return address")
     public void testGetFireStationAddressForStation() {
         when(fireStationDaoMock
         		.getStationsByStationIds(anyInt()))
         .thenReturn(fireStations);
         List<String> expectedAddresses = Arrays.asList("Test address", "Test2 address");

         List<String> addresses = fireStationService
         		.getAddressesByStation(anyInt());

         assertEquals(expectedAddresses, addresses);
         assertEquals("Test2 address", addresses.get(1));
         verify(fireStationDaoMock).getStationsByStationIds(anyInt());
     }
     
     
     @Test
     @DisplayName("Check (for empty fireStation value)"
     		+ " - Given a empty fireStation value,"
     		+ " when request get Address,"
     		+ " then return throws DataNotFoundException")
     public void TestGetFireStationAddressForStationWithNoValueProvided() {
         when(fireStationDaoMock
         		.getStationsByStationIds(anyInt()))
         .thenReturn(Collections.emptyList());

         assertThrows(DataNotFoundException.class, ()
         		->  fireStationService.getAddressesByStation(anyInt()));
     }

     
     
    }
     
}
