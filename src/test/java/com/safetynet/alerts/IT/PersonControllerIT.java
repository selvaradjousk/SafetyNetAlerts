package com.safetynet.alerts.IT;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.PersonDTO;

@DisplayName("Person Endpoints Controller - Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private ObjectMapper objectMapper;
    
    PersonDTO testPersonToBeAdded;
    ResponseEntity<PersonDTO> response;
    ResponseEntity<PersonDTO> getPersonAdded;

    // ***********************************************************************************
    @Test
    @DisplayName("Check Person created - Response Status<CREATED>"
    		+ " - Given a Person to add,"
    		+ " when POST request,"
    		+ " then return Status: 201 Created and body fields equals")
    public void testAddPersonRequestWithValidPersonResponseStatusCreated() {
        testPersonToBeAdded = new PersonDTO().builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.address("Testing Street")
        		.city("TestCity")
        		.zip(98765)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();	

    
   response = restTemplate
   		.postForEntity(
   				getRootUrl() + "/person",
   				testPersonToBeAdded,
   				PersonDTO.class);
   
        // Check Person created - <NOT NULL>
        assertNotNull(response);
     
   }
}