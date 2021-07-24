package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.service.AlertsUrlsService;

@DisplayName("FLOOD ALERT - GET HOUSES COVERED BY STATION Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(AlertsUrlsController.class)
class AlertsUrlsControllerGetHousesCoveredByStationTest {

	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertsUrlsService alertsService;
	
    private FloodDTO floodEndpointUrlDTO;
    
    @Test
    @DisplayName("Given VALID station number(s),"
    		+ " when GET Flood request,"
    		+ " then return - Status: 200 OK")
    public void testGetFloodRequestWithValidStationNumber() throws Exception {
        when(alertsService
        		.getHousesCoveredByStation(anyList()))
        .thenReturn(any(FloodDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/flood/stations?stations=1,2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(floodEndpointUrlDTO)))
                .andExpect(status()
                		.isOk());

        verify(alertsService)
        .getHousesCoveredByStation(anyList());
        verify(alertsService, times(1))
        .getHousesCoveredByStation(anyList());
    }
    

    @Test
    @DisplayName("GET FLOOD ALERT"
    		+ " - Given no station number value entered,"
    		+ " when GET Flood request,"
    		+ " then return - Status: 404 Bad Request")
    public void testGetFloodRequestWithNoStationNumberParameterValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/flood/stations?stations=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(floodEndpointUrlDTO)))
                .andExpect(status()
                		.isBadRequest());

        verify(alertsService, times(0))
        .getHousesCoveredByStation(anyList());
    }


}
