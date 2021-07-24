package com.safetynet.alerts.unittests.controller;

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

@DisplayName("PERSON ADD Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers= {PersonController.class, ExceptionHandlers.class})
public class PersonControllerAddTest {

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
    @DisplayName("Check (valid input)"
    		+ " - Given a Person to add,"
    		+ " when POST request,"
    		+ " then return Status: 201 Created")
    public void testAddPersonRequestWithValidPersonInfo() throws Exception {
    	when(personService
    			.addNewPerson(any(PersonDTO.class)))
    	.thenReturn(any(PersonDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(personDTO)))
                .andExpect(status()
                		.isCreated());

        verify(personService)
        .addNewPerson(any(PersonDTO.class));
        verify(personService, times(1))
        .addNewPerson(any(PersonDTO.class));
    }
    
    @Test
    @DisplayName("Check (without person FirstName)"
    		+ " - Given add a person without person Id,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddPersonRequestWithoutValidFirstName() throws Exception {
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
        		.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(personDTO)))
                .andExpect(status()
               		.isBadRequest());

        verify(personService, times(0))
        .addNewPerson(any(PersonDTO.class));
    }
    
    @Test
    @DisplayName("Check (without person LastName)"
    		+ " - Given add a person without person Id,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddPersonRequestWithoutLastName() throws Exception {
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
        		.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(personDTO)))
                .andExpect(status()
               		.isBadRequest());

        verify(personService, times(0))
        .addNewPerson(any(PersonDTO.class));
    }
    
    @Test
    @DisplayName("Check (without person Name)"
    		+ " - Given add a person without any person Ids,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddPersonRequestWithoutAnyNameIds() throws Exception {
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
        		.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(personDTO)))
                .andExpect(status()
               		.isBadRequest());

        verify(personService, times(0))
        .addNewPerson(any(PersonDTO.class));
    }
    
    @Test
    @DisplayName("Check(empty request body)"
    		+ " - Given add a person with an empty request body,"
    		+ " when POST request,"
    		+ " then return Status: 400 Bad Request)")
    public void testAddPersonRequestWithEmptyRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status()
                		.isBadRequest());

        verify(personService, times(0))
        .addNewPerson(any(PersonDTO.class));
    }
}
