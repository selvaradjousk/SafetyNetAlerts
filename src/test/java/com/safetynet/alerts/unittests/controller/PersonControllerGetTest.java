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
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.exception.ExceptionHandlers;
import com.safetynet.alerts.service.PersonService;

@DisplayName("PERSON GET Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers= {PersonController.class, ExceptionHandlers.class})
public class PersonControllerGetTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private ObjectMapper objectMapper;

    private PersonDTO personDTO;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        personDTO = PersonDTO.builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.address("1509 Culver St")
        		.city("Culver")
        		.zip(97451)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();
    }
    
    // ***************************************************************************************************

    @Test
    @DisplayName("Check (Valid input)"
    		+ " - Given VALID PERSON-ID,"
    		+ " when GET request (/person?firstName=Test FirstName&lastName=Test Last Name),"
    		+ " then return - Status: OK 200")
    public void testGetPersonRequestWithValidId() throws Exception {
        when(personService
        		.getPersonById(anyString(), anyString()))
        .thenReturn(personDTO);

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/person?firstName=Test FirstName&lastName=Test Last Name"))
                .andExpect(status()
                		.isOk());

        verify(personService)
        .getPersonById(anyString(), anyString());
        verify(personService, times(1))
        .getPersonById(anyString(), anyString());
    }

    @Test
    @DisplayName("Check (invalid input - no lastname"
    		+ " - Given INVALID PERSON-ID - without lastname,"
    		+ " when GET request (/person?firstName=Test FirstName&lastName=),"
    		+ " then return - Status: 400 Bad Request")
    public void testGetPersonRequestWithIdWithoutLastname() throws Exception {
    	
    	mockMvc.perform(MockMvcRequestBuilders
        		.get("/person?firstName=Test FirstName&lastName="))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .getPersonById(anyString(), anyString());
    }
    
    @Test
    @DisplayName("Check (invalid input - no firstname"
    		+ " - Given INVALID PERSON-ID - without firstname,"
    		+ " when GET request (/person?firstName=&lastName=Test LastName),"
    		+ " then return - Status: 400 Bad Request")
    public void testGetPersonRequestWithIdWithoutFirstname() throws Exception {
    	
    	mockMvc.perform(MockMvcRequestBuilders
        		.get("/person?firstName=Test FirstName&lastName="))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .getPersonById(anyString(), anyString());
    }
    
    @Test
    @DisplayName("Check (invalid input - no name"
    		+ " - Given INVALID PERSON-ID - without name,"
    		+ " when GET request (/person?firstName=&lastName=),"
    		+ " then return - Status: 400 Bad Request")
    public void testGetPersonRequestWithIdWithoutName() throws Exception {
    	
    	mockMvc.perform(MockMvcRequestBuilders
        		.get("/person?firstName=Test FirstName&lastName="))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .getPersonById(anyString(), anyString());
    }
}
