package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.exception.ExceptionHandlers;
import com.safetynet.alerts.service.PersonService;

@DisplayName("PERSON UPDATE Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers= {PersonController.class, ExceptionHandlers.class})
public class PersonControllerUpdateTest {

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
    		+ " - Given update a Person with VALID PERSON-ID,"
    		+ " when PUT request,"
    		+ " then return - Status: 200 OK")
    public void testUpdatePersonRequestWithValidPersonId() throws Exception {
        when(personService
        		.updateExistingPerson(any(PersonDTO.class)))
        .thenReturn(any(PersonDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(personDTO)))
                .andExpect(status()
                		.isOk());

        verify(personService)
        .updateExistingPerson(any(PersonDTO.class));
        verify(personService, times(1))
        .updateExistingPerson(any(PersonDTO.class));
    }
    
    @Test
    @DisplayName("Check (invalid input - no ids)"
    		+ " - Given update a person without person Id,"
    		+ " when PUT request,"
    		+ " then return - Status: 400 Bad Request")
    public void testUpdatePersonRequestWithoutPersonId() throws Exception {
        objectMapper = new ObjectMapper();
       personDTO = PersonDTO.builder()
       		.firstName("")
       		.lastName("")
       		.address("1509 Culver St")
       		.city("Culver")
       		.zip(97451)
       		.phone("111-111-1111")
       		.email("testemail@email.com")
       		.build();

        mockMvc.perform(MockMvcRequestBuilders
        		.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(personDTO)))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .updateExistingPerson(any(PersonDTO.class));
    }
    
    @Test
    @DisplayName("Check for ( No firstName)"
    		+ " - Given update a person without firstName,"
    		+ " when PUT request,"
    		+ " then return - Status: 400 Bad Request")
    public void testUpdatePersonRequestWithoutFirstName() throws Exception {
        objectMapper = new ObjectMapper();
       personDTO = PersonDTO.builder()
       		.firstName("")
       		.lastName("Test LastName")
       		.address("1509 Culver St")
       		.city("Culver")
       		.zip(97451)
       		.phone("111-111-1111")
       		.email("testemail@email.com")
       		.build();

        mockMvc.perform(MockMvcRequestBuilders
        		.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(personDTO)))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .updateExistingPerson(any(PersonDTO.class));
    }
    
    @Test
    @DisplayName("UCheck for ( No lastName)"
    		+ " - Given update a person without lastName,"
    		+ " when PUT request,"
    		+ " then return - Status: 400 Bad Request")
    public void testUpdatePersonRequestWithoutLastName() throws Exception {
        objectMapper = new ObjectMapper();
       personDTO = PersonDTO.builder()
       		.firstName("Test FirstName")
       		.lastName("")
       		.address("1509 Culver St")
       		.city("Culver")
       		.zip(97451)
       		.phone("111-111-1111")
       		.email("testemail@email.com")
       		.build();

        mockMvc.perform(MockMvcRequestBuilders
        		.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(personDTO)))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .updateExistingPerson(any(PersonDTO.class));
    }
    

    @Test
    @DisplayName("Check (No input of body)"
    		+ " - Given update a person with no request body content,"
    		+ " when PUT request,"
    		+ " then return Status: 400 Bad Request)")
    public void testPutRequestWithNoBodyContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.put("/person")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .updateExistingPerson(any(PersonDTO.class));
    }
}
