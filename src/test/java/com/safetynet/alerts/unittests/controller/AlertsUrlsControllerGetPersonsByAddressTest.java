package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.service.AlertsUrlsService;

@DisplayName("Alerts FIRE - GET PERSON BY ADDRESS Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(AlertsUrlsController.class)
class AlertsUrlsControllerGetPersonsByAddressTest {

	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertsUrlsService alertsService;
	
    private FireDTO fireEndpointUrlDTO;
    
    @Test
    @DisplayName("GET FIRE ALERT"
    		+ " - Given an address,"
    		+ " when GET Fire request,"
    		+ " then return - Status: 200 OK")
    public void testGetFireRequestWithValidAddress() throws Exception {
        when(alertsService
        		.getPersonsByAddress(anyString()))
        .thenReturn(any(FireDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/fire?address=Fire Address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(fireEndpointUrlDTO)))
                .andExpect(status().isOk());

        verify(alertsService)
        .getPersonsByAddress(anyString());
        verify(alertsService, times(1))
        .getPersonsByAddress(anyString());
    }

    
}
