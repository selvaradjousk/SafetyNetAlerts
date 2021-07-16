package com.safetynet.alerts.unittests.dao;


import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dao.FireStationDAO;
import com.safetynet.alerts.dao.IFireStationDAO;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.util.DataFileReader;

@DisplayName("FireStation DAO - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class FireStationDAOTest {

    private IFireStationDAO iFireStationDAO;

    private static FireStation firestation1;
    private static FireStation firestation2;

    @Mock
    private DataFileReader dataFileReader;

    @BeforeEach
    public void setUp() {
        firestation1 = new FireStation(2, "Test1 StreetName");
        firestation2 = new FireStation(1, "Test2 StreetName");

        when(dataFileReader
        		.getFireStationList())
        .thenReturn(Arrays.asList(firestation1, firestation2));

        iFireStationDAO = new FireStationDAO(dataFileReader);
    }

    
    // ***********************************************************************************

    @DisplayName("Test GET BY STATION ID")
    @Nested
    class TestGetFireStationById {  
        @BeforeEach
        public void init() {
        }
    
    @Test
    @DisplayName("Check (valid Station for Expected Value Match)"
    		+ " - Given an expected FireStation,"
    		+ " when get firestation,"
    		+ " then fireStation found should match expected FireStation")
    public void testGetFireStationById() {
        FireStation fireStationFound = iFireStationDAO
        		.getStationById(firestation2.getStationId(), firestation2.getAddress());

        assertEquals(firestation2, fireStationFound);
    }
    
    
    @Test
    @DisplayName("Check (FireStation does not exist)"
    		+ " - Given a FireStation does not exist already,"
    		+ " when get firestation,"
    		+ " then return null")
    public void testGetStationByIdForNonExistingStation() {
        FireStation fireStationNonExistant = new FireStation(1, "sfkjshd zerouezo");

        FireStation fireStationFound = iFireStationDAO
        		.getStationById(fireStationNonExistant.getStationId(),
        		fireStationNonExistant.getAddress());

        assertNull(fireStationFound);
    }
    
    }
    
    // ***********************************************************************************

    @DisplayName("Test GET FIRESTATION BY ADDRESS")
    @Nested
    class TestGetStationByAddress {  
        @BeforeEach
        public void init() {
        }
    @Test
    @DisplayName("Check for (valid input)"
    		+ " - Given an address,"
    		+ " when getStationByAddress,"
    		+ " then return FireStation at the address")
    public void testGetStationByAddress() {
        String address = "Test1 StreetName";

        FireStation fireFoundByAddress = iFireStationDAO
        		.getStationByAddress(address);

        assertEquals(firestation1, fireFoundByAddress);
    }

    @Test
    @DisplayName("Check (unvalid address input)"
    		+ " - Given an unregistered address,"
    		+ " when getStationByAddress,"
    		+ " then return null")
    public void testGetStationByAddressNonExistingAddress() {
        String unRegisteredAddress = "sjdflqsdlf jsdfljsld";

        FireStation fireSFoundByAddress = iFireStationDAO
        		.getStationByAddress(unRegisteredAddress);

        assertNull(fireSFoundByAddress);
    }
    
    }
    
    // ***********************************************************************************
    @DisplayName("Test GET BY STATION By Station Ids")
    @Nested
    class TestGetFireStationByIds {  
        @BeforeEach
        public void init() {
        }
    @Test
    @DisplayName("Check (for valid inputs)"
    		+ " - Given a station number,"
    		+ " when getStationsByStationIds,"
    		+ " then return FireStation associated with that station number")
    public void testGetStationsByStationIds() {
        List<FireStation> fireSFoundByStation = iFireStationDAO
        		.getStationsByStationIds(1);

        assertNotNull(fireSFoundByStation);
        assertTrue(fireSFoundByStation.contains(firestation2));
    }
    
    @Test
    @DisplayName("Check (for invalid Station Number)"
    		+ " - Given an invalid station number,"
    		+ " when getStationsByStationIds,"
    		+ " then return an empty fireStation list")
    public void testGetStationsByStationIdsForInvalidStationNumber() {
        List<FireStation> fireSFoundByStation = iFireStationDAO
        		.getStationsByStationIds(5);

        assertEquals(Collections.emptyList(), fireSFoundByStation);
    }
    
    @Test
    @DisplayName("GET BY STATION By Station Ids"
    		+ " - Given a negative station number,"
    		+ " when getStationsByStationIds,"
    		+ " then return an empty fireStation list")
    public void testGetStationsByStationIdsReturnEmptyFireStationListForNegativeInvalidValue() {
        List<FireStation> fireSFoundByStation = iFireStationDAO
        		.getStationsByStationIds(-5);

        assertEquals(Collections.emptyList(), fireSFoundByStation);
    }
    }
    
    // *********************************************************************************** 
    @Test
    @DisplayName("SAVE FIRE STATION -"
    		+ " Given a FireStation,"
    		+ " when save FireStation,"
    		+ " then FireStation should be saved successfully")
    public void testSaveFireStation() {
        FireStation fireStationToSave = new FireStation(2, "TestNew StreetNamet");

        FireStation fireStationSaved = iFireStationDAO.updateStation(fireStationToSave);

        assertEquals(fireStationToSave, fireStationSaved);
    }
    
    // ***********************************************************************************
    @Test
    @DisplayName("DELETE FIRE STATION"
    		+ " - Given a FireStation,"
    		+ " when delete FireStation,"
    		+ " then FireStation should be deleted successfully")
    public void testDeleteFireStation() {
        iFireStationDAO.deleteStationByMapping(firestation2);

        assertNull(iFireStationDAO.getStationById(firestation2.getStationId(), firestation2.getAddress()));
    }  
    
}
