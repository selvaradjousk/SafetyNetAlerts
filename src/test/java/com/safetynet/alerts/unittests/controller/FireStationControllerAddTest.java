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

@DisplayName("FIRESTATION ADD Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(FireStationController.class)
public class FireStationControllerAddTest {

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
        
        when(fireStationService
        		.getFireStationById(anyInt(), anyString()))
        .thenReturn(fireStationDTO);
    }


    
    @Test
    @DisplayName("Check (Valid FireStation Data)"
    		+ " - Given add firestation,"
    		+ " when POST request,"
    		+ " then return - Status: 201 Created")
    public void testAddStationRequestWithValidIdCheckStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(fireStationDTO)))
                .andExpect(status()
                		.isCreated());

        verify(fireStationService)
        .addNewFireStation(any(FireStationDTO.class));
    }
    
    @Test
    @DisplayName("Check (Address value empty)"
    		+ " - Given No address value,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddFireStationRequestWithMissingAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(fireStationDTOInvalidAddress)))
                .andExpect(status()
                		.isBadRequest());

        verify(fireStationService, times(0))
        .addNewFireStation(any(FireStationDTO.class));
    }
    
    
    @Test
    @DisplayName("Check (Input values invalid)"
    		+ " - Given Input values invalid,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddFireStationRequestWitinvalidIAndAddressd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(fireStationDTOInvalidIdAndAddress)))
                .andExpect(status()
                		.isBadRequest());

        verify(fireStationService, times(0))
        .addNewFireStation(any(FireStationDTO.class));
    }
    

    @Test
    @DisplayName("Check (empty request body)"
    		+ " - Given request body empty,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddStationREquestWithEmptyRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                		.isBadRequest());

        verify(fireStationService, times(0))
        .addNewFireStation(any(FireStationDTO.class));
    }
}
