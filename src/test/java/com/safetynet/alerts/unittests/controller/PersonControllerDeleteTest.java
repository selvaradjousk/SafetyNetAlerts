package com.safetynet.alerts.unittests.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.exception.ExceptionHandlers;
import com.safetynet.alerts.service.PersonService;

@DisplayName("PERSON DELETE Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers= {PersonController.class, ExceptionHandlers.class})
public class PersonControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;
   
 
 // ***************************************************************************************************
   
    @Test
    @DisplayName("Check (valid ID input)"
    		+ " - Given VALID PERSON-ID,"
    		+ " when DELETE request (/person?firstName={firstName}&lastName={lastName}),"
    		+ " then return - Status: 200 OK")
    public void testDeletePersonRequestWithValidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.delete("/person?firstName=Test FirstName&lastName=Test Last Name"))
                .andExpect(status()
                		.isOk());

        verify(personService)
        .deleteExistingPerson(anyString(), anyString());
        verify(personService, times(1))
        .deleteExistingPerson(anyString(), anyString());
    }
    
    @Test
    @DisplayName("Check (invalid person ID) input"
    		+ " - Given INVALID PERSON-ID without lastname,"
    		+ " when DELETE request (/person?firstName={firstName}&lastName={}),"
    		+ " then return - Status: 400 Bad Request")
    public void testDeletePersonRequestWithIdWithoutLastname() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.delete("/person?firstName=Test FirstName&lastName="))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .deleteExistingPerson(anyString(), anyString());
    }
    
    @Test
    @DisplayName("Check (INVALID PERSON-ID without firstname)"
    		+ " - Given INVALID PERSON-ID without firstname,"
    		+ " when DELETE request (/person?firstName={}&lastName={lastName}),"
    		+ " then return - Status: 400 Bad Request")
    public void testDeletePersonRequestWithIdWithoutFirstname() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.delete("/person?firstName=&lastName=Test LastName"))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .deleteExistingPerson(anyString(), anyString());
    }
    
    @Test
    @DisplayName("Check (INVALID inputs)"
    		+ " - Given INVALID PERSON-ID without No input Id,"
    		+ " when DELETE request (/person?firstName={}&lastName={}),"
    		+ " then return - Status: 400 Bad Request")
    public void testDeletePersonRequestWithIdWithoutIdInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.delete("/person?firstName=&lastName="))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .deleteExistingPerson(anyString(), anyString());
    }
}
