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
import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.service.AlertsUrlsService;


@DisplayName("Alerts GET CHILD BY ADDRESS Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(AlertsUrlsController.class)
class AlertsUrlsControllerGetChildByAddressTest {



	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertsUrlsService alertsService;
	
    private ChildAlertDTO childAlertEndpointUrlDTO;
    
    @Test
    @DisplayName("Given VALID ADDRESS,"
    		+ " when GET request,"
    		+ " then return - Status: 200 OK")
    public void testGetChildAlertRequestWithValidAddress() throws Exception {
        when(alertsService.getChildByAddress(anyString()))
        .thenReturn(any(ChildAlertDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/childAlert?address=Valid Address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(childAlertEndpointUrlDTO)))
                .andExpect(status()
                		.isOk());

        verify(alertsService)
        .getChildByAddress(anyString());
        verify(alertsService, times(1))
        .getChildByAddress(anyString());
    }

}
