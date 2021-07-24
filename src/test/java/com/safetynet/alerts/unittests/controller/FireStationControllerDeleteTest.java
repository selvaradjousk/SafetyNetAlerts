package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

@DisplayName("FIRESTATION DELETE Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(FireStationController.class)
public class FireStationControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private ObjectMapper objectMapper;

    @Mock
    private FireStationDTO fireStationDTO;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        fireStationDTO = FireStationDTO.builder()
        		.stationId(3)
        		.address("Test StreetName")
        		.build();

        
    }
    
 
    
    // ***************************************************************************************************
    
  @Test
  @DisplayName("Test DELETE FIRESTATION valid firestation id"
  		+ " - Given a valid FireStation ID values,"
  		+ " when DELETE request,"
  		+ " then return - Status: 200 OK")
  public void testDeleteStationRequestWithValidInputValues() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders
      		.post("/firestation")
              .contentType(MediaType.APPLICATION_JSON)
              .content(displayAsJsonString(fireStationDTO)))
              .andExpect(status()
              		.isCreated());
	  
      mockMvc.perform(MockMvcRequestBuilders
      		.delete("/firestation?station=3&address=Test StreetName"))
              .andExpect(status()
              		.isOk());

      verify(fireStationService)
      .deleteExistingStation(anyInt(), anyString());
      verify(fireStationService, times(1))
      .deleteExistingStation(anyInt(), anyString());
      
  }
  
  @Test
  @DisplayName("Test DELETE FIRESTATION empty address input"
  		+ " - Given without address input value,"
  		+ " when DELETE request,"
  		+ " then return - Status: 400 Bad Request")
  public void testDeleteStationRequestWithoutAddressValues() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders
      		.delete("/firestation?address=&station=3"))
              .andExpect(status()
              		.isBadRequest());

      verify(fireStationService, times(0))
      .deleteExistingStation(anyInt(), anyString());
  }
  
  @Test
  @DisplayName("Test DELETE FIRESTATION empty id input"
  		+ " - Given without id input value,"
  		+ " when DELETE request,"
  		+ " then return - Status: 400 Bad Request")
  public void testDeleteStationRequestWithoutIdValue() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders
      		.delete("/firestation?address=Test StreetName&station="))
              .andExpect(status()
              		.isBadRequest());

      verify(fireStationService, times(0))
      .deleteExistingStation(anyInt(), anyString());
  }
  
  @Test
  @DisplayName("Test DELETE FIRESTATION empty address and id inputs"
  		+ " - Given without input value,"
  		+ " when DELETE request,"
  		+ " then return - Status: 400 Bad Request")
  public void testDeleteStationRequestWithoutValues() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders
      		.delete("/firestation?address=&station="))
              .andExpect(status()
              		.isBadRequest());

      verify(fireStationService, times(0))
      .deleteExistingStation(anyInt(), anyString());
  }
}
