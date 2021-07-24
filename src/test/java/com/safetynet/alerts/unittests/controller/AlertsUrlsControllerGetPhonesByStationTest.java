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
import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.service.AlertsUrlsService;


@DisplayName("Alerts GET PHONE BY STATION Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(AlertsUrlsController.class)
class AlertsUrlsControllerGetPhonesByStationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertsUrlsService alertsService;
	
    private PhoneAlertDTO phoneAlertEndpointUrlDTO;
    
    @Test
    @DisplayName("GET PHONE ALERT"
    		+ " - Given a valid station number,"
    		+ " when GET request,"
    		+ " then return - Status: 200 OK")
    public void testGetPhoneAlertRequestWithValidStationNumber() throws Exception {
        when(alertsService.getPhonesByStation(anyInt()))
        .thenReturn(any(PhoneAlertDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/phoneAlert?firestation=2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(phoneAlertEndpointUrlDTO)))
                .andExpect(status().isOk());

        verify(alertsService)
        .getPhonesByStation(anyInt());
        verify(alertsService, times(1))
        .getPhonesByStation(anyInt());
    }
}
