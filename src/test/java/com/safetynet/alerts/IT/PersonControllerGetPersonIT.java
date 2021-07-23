package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.PersonDTO;

@DisplayName("PERSON GET Endpoint Controller - Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerGetPersonIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private ObjectMapper objectMapper;
    
    
    PersonDTO testPersonToBeAdded, testPersonToUpdate, personToAddMissingId;
    ResponseEntity<PersonDTO> response;
    ResponseEntity<PersonDTO> getPersonAdded;
    
    private final static String PERSON_ID_URL = "/person?firstName={firstName}&lastName={lastName}";

    @BeforeEach
  public void setUp() {
    	objectMapper = new ObjectMapper();
        testPersonToBeAdded = new PersonDTO().builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.address("Testing Street")
        		.city("TestCity")
        		.zip(98765)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();
        
        personToAddMissingId = new PersonDTO().builder()
        		.firstName("")
        		.lastName("")
        		.address("Test Address")
        		.city("Test City")
        		.zip(98765)
        		.phone("987-654-3210")
        		.email("testemail@email.com")
        		.build();
        
        
  	   restTemplate
	   		.postForEntity(
	   				getRootUrl() + "/person",
	   				testPersonToBeAdded,
	   				PersonDTO.class);
  	   
        response = restTemplate
     		   .getForEntity(getRootUrl() + PERSON_ID_URL,
                PersonDTO.class,
                testPersonToBeAdded.getFirstName(),
                testPersonToBeAdded.getLastName());
	
    }
        
        @AfterEach
        public void finish() {
                 
            restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToBeAdded.getFirstName(), testPersonToBeAdded.getLastName()); 
        }
        
        // ***********************************************************************************     
        
        @Test
        @DisplayName("Check - <RESPONSE NOT NULL>"
        		+ " - Given a Person,"
        		+ " when GET request,"
        		+ " then Person data returned is not null")
        public void testGetPersonRequestWithValidPersonResponseNotNull() {

           assertNotNull(response);
        }
       
        
        @Test
        @DisplayName("Check - <RESPONSE HEADER NOT NULL>"
        		+ " - Given a Person,"
        		+ " when GET request,"
        		+ " then response header not null")
        public void testGetPersonRequestWithValidPersonResponseHeaderNotNull() {
                assertNotNull(response.getHeaders());
                assertEquals("application/json", (response.getHeaders().getContentType()).toString());
        }

        @Test
        @DisplayName("Check - <RESPONSE BODY NOT NULL>"
        		+ " - Given a Person,"
        		+ " when GET request,"
        		+ " then response body is not Null")
        public void testGetPersonRequestWithValidResponseBodyNotNull() {
            assertNotNull(response.getBody());
        }
        
        @Test
        @DisplayName("Check - <NOT NULL - FirstName, LastName Values>"
        		+ " - Given a Person,"
        		+ " when GET request,"
        		+ " then return response body firstName & LastName param value not null")
        public void testGetPersonRequestWithValidPersonParamValueNotNull() {
            
            assertNotNull(response.getBody().getFirstName());
            assertNotNull(response.getBody().getLastName());
        }
        
        @Test
        @DisplayName("Check - Response Status<200 OK>"
        		+ " - Given a Person,"
        		+ " when GET request,"
        		+ " then return Status: 200 OK")
        public void testGetPersonRequestWithValidPersonResponseStatusOk() {
            assertEquals((HttpStatus.OK), response.getStatusCode());
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
       }  
        
        
        @Test
        @DisplayName("Check - <VALIDATE FIELDS EQUAL>"
        		+ " - Given a Person,"
        		+ " when GET request,"
        		+ " then return response fields equals expected added Person")
        public void testGetPersonRequestWithValidPersonThenSimilarToAddedPersonValues() {
            assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(testPersonToBeAdded);
        }
        
        @Test
        @DisplayName("Check - <MISSING PERSON ID>"
        		+ "Given a Person with missing ID,"
        		+ " when GET request,"
        		+ " then return Reponse Status: 4xx BAD REQUEST")
        public void testGetPersonMissingId() {
        	
            restTemplate
            .postForEntity(getRootUrl() + "/person",
            		personToAddMissingId,
            		PersonDTO.class);
            
        	response = restTemplate
        			.getForEntity(getRootUrl() + PERSON_ID_URL,
                    PersonDTO.class,
                    "",
                    personToAddMissingId.getLastName());
        	
            assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
            assertNull(response.getBody().getFirstName());
            
        }   
        

        @Test
        @DisplayName("Check - <INVALID PERSON ID - 409>"
        		+ "Given a Person with invalid ID,"
        		+ " when GET request,"
        		+ " then return Reponse Status: 409 NOT FOUND REQUEST")
        public void testGetPersonForInvalidID() {
        	
       
        	response = restTemplate
        			.getForEntity(getRootUrl() + PERSON_ID_URL,
                    PersonDTO.class,
                    "fdsqfsdfs",
                    "sdqfsqfdqsf");
        	
            assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
            assertNull(response.getBody().getFirstName());
            
        }
     
}


