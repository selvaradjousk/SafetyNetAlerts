package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.alerts.controller.AlertsUrlsController;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.service.AlertsUrlsService;

@DisplayName("Alerts GET PERSON BY STATION Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(AlertsUrlsController.class)
class AlertsUrlsControllerGetPersonsByStationTest {

	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertsUrlsService alertsService;
	
    private PersonsByStationDTO personsByStationEndpointUrlDTO;
    

    @Test
    @DisplayName("Given a VALID STATION NUMBER,"
    		+ " when GET request (/firestation?stationNumber=1),"
    		+ " then return - Status: 200 OK")
    public void testGetPersonsByStationRequestWithValidStationId() throws Exception {
        when(alertsService
        		.getPersonsByStation(anyInt()))
        .thenReturn(any(PersonsByStationDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/firestation?stationNumber=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(personsByStationEndpointUrlDTO)))
                .andExpect(status()
                		.isOk());

        verify(alertsService)
        .getPersonsByStation(anyInt());
        verify(alertsService, times(1))
        .getPersonsByStation(anyInt());
    }
    
    @Test
    @DisplayName("Given an EMPTY STATION NUMBER,"
    		+ " when GET request (/firestation?stationNumber=),"
    		+ " then return - Status: 400 Bad Request")
    public void testGetPersonsByStationRequestWithEmptyStationNumberAsInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/firestation?stationNumber=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(personsByStationEndpointUrlDTO)))
                .andExpect(status()
                		.isBadRequest());

        verify(alertsService, times(0))
        .getPersonsByStation(anyInt());
    }

}
