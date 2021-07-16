package com.safetynet.alerts.unittests.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

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
    
    
    
    
    }
    
}
