package com.safetynet.alerts.unittests.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.PersonEndpointController;
import com.safetynet.alerts.dto.PersonEndpointDTO;
import com.safetynet.alerts.service.PersonEndpointService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonEndpointController.class)
public class PersonEndpointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonEndpointService personEndpointService;

    private ObjectMapper objectMapper;

    private PersonEndpointDTO personEndpointDTO;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        personEndpointDTO = PersonEndpointDTO.builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.address("1509 Culver St")
        		.city("Culver")
        		.zip(97451)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();
    }
    
    @Test
    @DisplayName("GET PERSON"
    		+ " - Given VALID PERSON-ID,"
    		+ " when GET request (/person?firstName=Test FirstName&lastName=Test Last Name),"
    		+ " then return - Status: OK 200")
    public void testGetPersonRequestWithValidId() throws Exception {
        when(personEndpointService
        		.getPersonById(anyString(), anyString()))
        .thenReturn(personEndpointDTO);

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/person?firstName=Test FirstName&lastName=Test Last Name"))
                .andExpect(status()
                		.isOk());

        verify(personEndpointService)
        .getPersonById(anyString(), anyString());
        verify(personEndpointService, times(1))
        .getPersonById(anyString(), anyString());
    }
}