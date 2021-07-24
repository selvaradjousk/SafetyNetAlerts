package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FireStationController;
import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.service.FireStationService;

@DisplayName("FIRESTATION UPDATE Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(FireStationController.class)
public class FireStationControllerUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private ObjectMapper objectMapper;

    @Mock
    private FireStationDTO fireStationDTO, fireStationDTOInvalidAddress, fireStationDTOInvalidId,
    fireStationDTOInvalidIdAndAddress;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        fireStationDTO = FireStationDTO.builder()
        		.stationId(3)
        		.address("Test StreetName")
        		.build();
        
        fireStationDTOInvalidAddress = FireStationDTO.builder()
        		.stationId(3)
        		.address("")
        		.build();
        
        fireStationDTOInvalidAddress = FireStationDTO.builder()
        		.stationId(3)
        		.address("")
        		.build();
        
        fireStationDTOInvalidIdAndAddress = FireStationDTO.builder()
        		.stationId(999)
        		.address("")
        		.build();
    }
    
    
    // ***************************************************************************************************
        
        
        @Test
        @DisplayName("Check (for valid input)"
        		+ " - Given valid input, when PUT request,"
        		+ " then return - Status: 200 OK")
        public void testUpdateStationWithValidInput() throws Exception {
            when(fireStationService
            		.updateExistingStation(any(FireStationDTO.class)))
            .thenReturn(any(FireStationDTO.class));
            mockMvc.perform(MockMvcRequestBuilders
            		.put("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(displayAsJsonString(fireStationDTO)))
                    .andExpect(status()
                    		.isOk());

            verify(fireStationService)
            .updateExistingStation(any(FireStationDTO.class));
            verify(fireStationService, times(1))
            .updateExistingStation(any(FireStationDTO.class));
        }

        @Test
        @DisplayName("Check (Invalid Address)"
        		+ " - Given invalid Address, when PUT request,"
        		+ " then return - Status: 400 Bad Request")
        public void testUpdateStationRequestWithInValidAddress() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
            		.put("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(displayAsJsonString(fireStationDTOInvalidAddress)))
                    .andExpect(status()
                    		.isBadRequest());

            verify(fireStationService, times(0))
            .updateExistingStation(any(FireStationDTO.class));
        }
        
        @Test
        @DisplayName("Check (Invalid Address and Id)"
        		+ " - Given invalid Address, when PUT request,"
        		+ " then return - Status: 400 Bad Request")
        public void testUpdateStationRequestWithInValidAddressAndId() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
            		.put("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(displayAsJsonString(fireStationDTOInvalidIdAndAddress)))
                    .andExpect(status()
                    		.isBadRequest());

            verify(fireStationService, times(0))
            .updateExistingStation(any(FireStationDTO.class));
        }
          
        @Test
        @DisplayName("Check (for empty input)"
        		+ " - Given empty body request,"
        		+ " when PUT request,"
        		+ " then return Status: 400 Bad Request")
        public void testUpdateStationRequestWithEmptyBody() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
            		.put("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(displayAsJsonString("")))
                    .andExpect(status()
                    		.isBadRequest());

            verify(fireStationService, times(0))
            .updateExistingStation(any(FireStationDTO.class));
        }  
}
