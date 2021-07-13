package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

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
import org.springframework.http.HttpStatus;
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
    
    PersonDTO testPersonToBeAdded, personToAddMissingId;
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
        
        personToAddMissingId = new PersonDTO().builder()
        		.firstName("")
        		.lastName("")
        		.address("Test Address")
        		.city("Test City")
        		.zip(98765)
        		.phone("987-654-3210")
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
                 
            restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToBeAdded.getFirstName(), testPersonToBeAdded.getLastName()); 
        }
    
    // ***********************************************************************************
    @Test
    @DisplayName("Check - <RESPONSE NOT NULL>"
    		+ " - Given a Person to add,"
    		+ " when POST request,"
    		+ " then Person data created is not null")
    public void testAddPersonRequestWithValidPersonResponseNotNull() {


    
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
    
    @Test
    @DisplayName("Check - <RESPONSE BODY NOT NULL>"
    		+ " - Given a Person to add,"
    		+ " when POST request,"
    		+ " then response body is not Null")
    public void testAddPersonRequestWithValidResponseBodyNotNull() {
            response = restTemplate
               		.postForEntity(
               				getRootUrl() + "/person",
               				testPersonToBeAdded,
               				PersonDTO.class);
      //When Post created CHECKs
        assertNotNull(response.getBody());
    }
    
    @Test
    @DisplayName("Check - <NOT NULL - FirstName, LastName Values>"
    		+ " - Given a Person to add,"
    		+ " when POST request,"
    		+ " then return response body firstName & LastName param value not null")
    public void testAddPersonRequestWithValidPersonParamValueNotNull() {
            response = restTemplate
               		.postForEntity(
               				getRootUrl() + "/person",
               				testPersonToBeAdded,
               				PersonDTO.class);
      //When Post created CHECKs
        assertNotNull(response.getBody().getFirstName());
        assertNotNull(response.getBody().getLastName());
    }

    @Test
    @DisplayName("Check - Response Status<201 CREATED>"
    		+ " - Given a Person to add,"
    		+ " when POST request,"
    		+ " then return Status: 201 Created")
    public void testAddPersonRequestWithValidPersonResponseStatusCreated() {
        response = restTemplate
           		.postForEntity(
           				getRootUrl() + "/person",
           				testPersonToBeAdded,
           				PersonDTO.class);
      //When Post created CHECKs
        assertEquals((HttpStatus.CREATED), response.getStatusCode());
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("request status", HttpStatus.CREATED.value(), response.getStatusCodeValue());
   }
    
    @Test
    @DisplayName("Check - <VALIDATE FIELDS EQUAL>"
    		+ " - Given a Person to add,"
    		+ " when POST request,"
    		+ " then return response fields equals added Person")
    public void testAddPersonRequestWithValidPersonThenSimilarToAddedPersonValues() {
        response = restTemplate
           		.postForEntity(
           				getRootUrl() + "/person",
           				testPersonToBeAdded,
           				PersonDTO.class);
      //When Post created CHECKs
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(testPersonToBeAdded);
    }
    
    @Test
    @DisplayName("Check - able to <Retrieve ADDED PERSON DATA>"
    		+ " - Given a Person to add,"
    		+ " when POST request, followed by GET PERSON"
    		+ " then return Person Added Before successfully")
    public void testAddPersonRequestWithValidPersonThenOnGetRequestPersonAddedREturnedSuccessfully() {
            response = restTemplate
               		.postForEntity(
               				getRootUrl() + "/person",
               				testPersonToBeAdded,
               				PersonDTO.class);
        getPersonAdded = restTemplate.getForEntity(
           		getRootUrl() + "/person?firstName="+testPersonToBeAdded.getFirstName()+"&"+"lastName="+testPersonToBeAdded.getLastName(),
   				PersonDTO.class);
    	//Then Person can be retrieved CHECKs
        assertTrue(getPersonAdded.getStatusCode().is2xxSuccessful());   
    } 
    
    
    @Test
    @DisplayName("Check - <CONFLICT STATUS - ADD EXISTING PERSON>"
    		+ "Given a registered Person,"
    		+ " when POST request,"
    		+ " then return Response Status: 409 CONFLICT" +
            "should be returned")
    public void testAddPersontRequestExistingPerson() {

        restTemplate.postForEntity(
        		getRootUrl() + "/person",
        		testPersonToBeAdded,
        		PersonDTO.class);
        
        response = restTemplate
           		.postForEntity(
           				getRootUrl() + "/person",
           				testPersonToBeAdded,
           				PersonDTO.class);

        assertThat(response.getBody()).isNotSameAs(testPersonToBeAdded);
        assertEquals("request status", HttpStatus.CONFLICT.value(), response.getStatusCodeValue());

    }
    
    @Test
    @DisplayName("Check - <MISSING PERSON ID>"
    		+ "Given a Person with missing ID,"
    		+ " when POST request,"
    		+ " then return Reponse Status: 4xx BAD REQUEST")
    public void testAddPersonMissingId() {

        response = restTemplate
        		.postForEntity(
        				getRootUrl() + "/person",
        				personToAddMissingId,
        				PersonDTO.class);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertNull(response.getBody().getFirstName());
        
    }
    
    
    }
}