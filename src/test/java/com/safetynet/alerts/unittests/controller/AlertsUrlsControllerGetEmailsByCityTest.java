package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.any;
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
import com.safetynet.alerts.dto.CommunityEmailDTO;
import com.safetynet.alerts.service.AlertsUrlsService;


@DisplayName("Alerts GET COMMUNITY EMAIL Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(AlertsUrlsController.class)
class AlertsUrlsControllerGetEmailsByCityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertsUrlsService alertsService;
	
    private CommunityEmailDTO communityEmailEndpointUrlDTO;
    
    @Test
    @DisplayName("Given a valid city,"
    		+ " when GET CommunityEmail request,"
    		+ " then return - Status: 200 OK")
    public void testGetCommunityEmailRequestWithValidCityName() throws Exception {
        when(alertsService
        		.getEmailsByCity(anyString()))
        .thenReturn(any(CommunityEmailDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/communityEmail?city=Some city")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(communityEmailEndpointUrlDTO)))
                .andExpect(status()
                		.isOk());

        verify(alertsService)
        .getEmailsByCity(anyString());
        verify(alertsService, times(1))
        .getEmailsByCity(anyString());
    }
}
