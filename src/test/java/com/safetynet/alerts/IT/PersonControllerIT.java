package com.safetynet.alerts.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
    
    private final static String PERSON_ID_URL = "/person?firstName={firstName}&lastName={lastName}";

    @BeforeEach
  public void setUp() {
    	
        testPersonToBeAdded = new PersonDTO().builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.address("Testing Street")
        		.city("TestCity")
        		.zip(98765)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();	
    	
    }
    
    @DisplayName("IT - ADD NEW PERSON")
    @Nested
    @TestMethodOrder(OrderAnnotation.class)
    class AddNewPersonIT {  
    	
        @BeforeEach
        public void init() {
 
        }
        
        @AfterEach
        public void finish() {
//            getPersonAdded = restTemplate.getForEntity(
//               		getRootUrl() + "/person?firstName="+testPersonToBeAdded.getFirstName()+"&"+"lastName="+testPersonToBeAdded.getLastName(),
//       				PersonDTO.class);
                   
            restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToBeAdded.getFirstName(), testPersonToBeAdded.getLastName()); 
        }
    
    // ***********************************************************************************
    @Test
    @DisplayName("Check - <RESPONSE NOT NULL>"
    		+ " - Given a Person to add,"
    		+ " when POST request,"
    		+ " then Person data created is not null")
    public void testAddPersonRequestWithValidPersonResponseStatusCreated() {


    
   response = restTemplate
   		.postForEntity(
   				getRootUrl() + "/person",
   				testPersonToBeAdded,
   				PersonDTO.class);

        assertNotNull(response);
    }
    
    @Test
    @DisplayName("Check - <RESPONSE HEADER NOT NULL>"
    		+ " - Given a Person to add,"
    		+ " when POST request,"
    		+ " then response header not null")
    public void testAddPersonRequestWithValidPersonResponseHeaderNotNull() {
            response = restTemplate
               		.postForEntity(
               				getRootUrl() + "/person",
               				testPersonToBeAdded,
               				PersonDTO.class);
            assertNotNull(response.getHeaders());
            assertEquals("application/json", (response.getHeaders().getContentType()).toString());
    }   
    
    }
}