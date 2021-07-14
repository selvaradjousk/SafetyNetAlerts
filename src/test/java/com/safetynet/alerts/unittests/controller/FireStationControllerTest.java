package com.safetynet.alerts.unittests.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FireStationController;
import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.service.FireStationService;


@ExtendWith(SpringExtension.class)
@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private ObjectMapper objectMapper;

    
    private FireStationDTO fireStationDTO;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        fireStationDTO = FireStationDTO.builder()
        		.stationId(3)
        		.address("Test StreetName")
        		.build();
    }

    @Test
    @DisplayName("GET STATION"
    		+ " - Given valid key ID,"
    		+ " when GET request,"
    		+ " then return - Status: 200 OK")
    public void testGetStationRequestWithValidInputValues() throws Exception {
        when(fireStationService
        		.getFireStationById(anyInt(), anyString()))
        .thenReturn(fireStationDTO);

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/fireStation?station=3&address=Test StreetName"))
                .andExpect(status()
                		.isOk());

        verify(fireStationService)
        .getFireStationById(anyInt(), anyString());
        verify(fireStationService, times(1))
        .getFireStationById(anyInt(), anyString());
    }
    
    @Test
    @DisplayName("GET STATION (No input values)"
    		+ " - Given no input,"
    		+ " when GET request,"
    		+ " then return - Status: 400 BAD REQUEST")
    public void testGetStationRequestWithWithoutInputValues() throws Exception {
        when(fireStationService
        		.getFireStationById(anyInt(), anyString()))
        .thenReturn(fireStationDTO);

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/fireStation?station=&address="))
                .andExpect(status()
                		.isBadRequest());
    }
    
    @Test
    @DisplayName("GET STATION (No Station ID)"
    		+ " - Given no station ID,"
    		+ " when GET request,"
    		+ " then return - Status: 400 BAD REQUEST")
    public void testGetStationRequestWithWithoutStationId() throws Exception {
        when(fireStationService
        		.getFireStationById(anyInt(), anyString()))
        .thenReturn(fireStationDTO);

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/fireStation?station=&address=Test StreetName"))
                .andExpect(status()
                		.isBadRequest());
    } 
    
    @Test
    @DisplayName("GET STATION (No Station Address)"
    		+ " - Given no station Address,"
    		+ " when GET request,"
    		+ " then return - Status: 400 BAD REQUEST")
    public void testGetStationRequestWithWithoutStationAddress() throws Exception {
        when(fireStationService
        		.getFireStationById(anyInt(), anyString()))
        .thenReturn(fireStationDTO);

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/fireStation?station=3&address="))
                .andExpect(status()
                		.isBadRequest());
    } 
    
}
