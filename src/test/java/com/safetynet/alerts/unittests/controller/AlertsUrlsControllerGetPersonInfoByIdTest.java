package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.PersonInfo;
import com.safetynet.alerts.service.AlertsUrlsService;


@DisplayName("Alerts GET PERSON INFO BY ID Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(AlertsUrlsController.class)
class AlertsUrlsControllerGetPersonInfoByIdTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertsUrlsService alertsService;
	
    private PersonInfoDTO personInfoEndPointUrlDTO;
    
    @Test
    @DisplayName("Given valid person Id,"
    		+ " when Get PersonInfo request,"
    		+ " then return - Status: 200 OK")
    public void testGetPersonInfoRequestWithValidPersonId() throws Exception {
    	personInfoEndPointUrlDTO = new PersonInfoDTO(
        		Arrays.asList(
        				new PersonInfo(
        				"Last Name",
        				"My Street",
        				50,
        				"myemail@email.com",
        				Arrays.asList("some medication"),
        				Arrays.asList("some allergy"))));

        when(alertsService
        		.getInfoPersonByIdentity(anyString(), anyString()))
        .thenReturn(personInfoEndPointUrlDTO);

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/personInfo?=firstName=John&lastName=Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(personInfoEndPointUrlDTO)))
                .andExpect(status().isOk());

        verify(alertsService)
        .getInfoPersonByIdentity(anyString(), anyString());
        verify(alertsService, times(1))
        .getInfoPersonByIdentity(anyString(), anyString());
    }
    
    @Test
    @DisplayName("Given incomplete person Id,"
    		+ " when PersonInfo request,"
    		+ " then return - Status: 404 Bad Request")
    public void testGetPersonInfoRequestWithInvalidPersonId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/personInfo?=firstName=John&lastName=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(personInfoEndPointUrlDTO)))
                .andExpect(status()
                		.isBadRequest());

        verify(alertsService, times(0))
        .getInfoPersonByIdentity(anyString(), anyString());
    }
}
