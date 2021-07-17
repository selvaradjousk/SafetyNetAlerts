package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import com.safetynet.alerts.service.PersonService;

@DisplayName("Person Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

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
    @DisplayName("Test GET PERSON")
    @Nested
    class TestGetPerson { 
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
    
    // ***************************************************************************************************
    @DisplayName("Test ADD PERSON")
    @Nested
    class TestAddPerson {  
    
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
    
    // ***************************************************************************************************
    @DisplayName("Test UPDATE PERSON")
    @Nested
    class TestUpdatePerson {  
    
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
    
 // ***************************************************************************************************
    @DisplayName("Test DELETE PERSON")
    @Nested
    class TestDeletePerson {  
    
    
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
}
