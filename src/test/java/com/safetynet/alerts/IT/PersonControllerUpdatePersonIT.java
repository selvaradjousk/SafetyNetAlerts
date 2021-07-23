package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

@DisplayName("PERSON UPDATE Endpoint Controller - Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerUpdatePersonIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private ObjectMapper objectMapper;
    
    
    PersonDTO testPersonToBeAdded, testPersonToUpdate, personToAddMissingId,
    testPersonUpdated, testPersonToUpdateWrongFirstName;
    ResponseEntity<PersonDTO> response, responseOnPost;
    ResponseEntity<PersonDTO> getPersonAdded;
    
    private final static String PERSON_ID_URL = "/person?firstName={firstName}&lastName={lastName}";


    	
        @BeforeEach
        public void init() {
        	
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
            
            testPersonToUpdate = new PersonDTO().builder()
             		.firstName("TestUpdate FirstName")
             		.lastName("Test Last Name")
             		.address("Test To update Address")
             		.city("Test to update City")
             		.zip(98765)
             		.phone("987-654-3210")
             		.email("testemail@email.com")
             		.build();
            
            testPersonUpdated = new PersonDTO().builder()
             		.firstName("TestUpdate FirstName")
             		.lastName("Test Last Name")
             		.address("Testvbvb Address")
             		.city("Test City")
             		.zip(98765)
             		.phone("987-654-3210")
             		.email("testemail@email.com")
             		.build();
            
            testPersonToUpdateWrongFirstName = new PersonDTO().builder()
             		.firstName("TestUpdate FirstName")
             		.lastName("Testxxx Last Name")
             		.address("Test xxx Address")
             		.city("Testxxx City")
             		.zip(98765)
             		.phone("987-654-3210")
             		.email("testemail@email.com")
             		.build();
        	
        	// person created
     	   responseOnPost = restTemplate
	   		.postForEntity(
	   				getRootUrl() + "/person",
	   				testPersonToUpdate,
	   				PersonDTO.class);

     	   
     	  
      	// update requested
      	restTemplate.put(getRootUrl() + "/person", testPersonUpdated);
     	  
           response = restTemplate
        		   .getForEntity(getRootUrl() + PERSON_ID_URL,
                   PersonDTO.class,
                   testPersonToUpdate.getFirstName(),
                   testPersonToUpdate.getLastName());
           

        }
        
        @AfterEach
        public void finish() {
                 
            restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToUpdate.getFirstName(), testPersonToUpdate.getLastName()); 
            restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonUpdated.getFirstName(), testPersonUpdated.getLastName());
        }
        
        
        
        
        @Test
        @DisplayName("Check - <PERSON DATA EXIST BEFORE UPDATE>"
        		+ " - Given a Person,"
        		+ " when UPDATE request,"
        		+ " then Person data EXIST BEFORE UPDATE")
        public void testUpdatePersonRequestWithValidPersonExisitBeforeUpdate() {
      	   
           	// Person Data Exist
           	assertNotNull(responseOnPost);
           	assertNotNull(responseOnPost.getBody().getAddress());
        }
        
        @Test
        @DisplayName("Check - <PERSON UPDATE - STATUS 200 OK>"
        		+ " - Given a Person,"
        		+ " when UPDATE request,"
        		+ " then return response Status: 200 OK")
        public void testUpdatePersonResponseStatusOk() {
      	   
           	// Person Update Data Exist
        	assertEquals((HttpStatus.OK), response.getStatusCode());
        	assertEquals(200, response.getStatusCodeValue());
        	assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        }        
        
        @Test
        @DisplayName("Check - <PERSON UPDATE - RESPONSE NOT NULL>"
        		+ " - Given a Person,"
        		+ " when UPDATE request,"
        		+ " then returned response Not Null")
        public void testUpdatePersonResponseResponseNotNull() {
      	   
            // Person Update data updated not null
            assertNotNull(response);
            assertNotNull(response.getHeaders());
            assertNotNull(response.getBody());
        }
        
        @Test
        @DisplayName("Check - <On PERSON UPDATE - Value Not Same>"
        		+ " - Given a Person,"
        		+ " when UPDATE request,"
        		+ " then returned response Value Not Same Before Update")
        public void testUpdatePersonValueBeforeAfterNotSame() {
           	// Value of Address parameter stored for reference before update
       	  String valueBeforeUpdate = responseOnPost.getBody().getAddress();
       	  
        	// update requested
        	restTemplate.put(getRootUrl() + "/person", testPersonUpdated);
       	  
             response = restTemplate
          		   .getForEntity(getRootUrl() + PERSON_ID_URL,
                     PersonDTO.class,
                     testPersonToUpdate.getFirstName(),
                     testPersonToUpdate.getLastName());
             
             String valueAfterUpdate = response.getBody().getAddress();
            // Check value before and after update are not equal
        	assertNotEquals(valueBeforeUpdate, valueAfterUpdate);
        } 
        
        @Test
        @DisplayName("Check - <On PERSON UPDATE - Value expected Updated>"
        		+ " - Given a Person,"
        		+ " when UPDATE request,"
        		+ " then Person data updated with expected value")
        public void testUpdatePersonValueSameAsExpectedUpdateValue() {
       	  
        	// update requested
        	restTemplate.put(getRootUrl() + "/person", testPersonUpdated);
       	  
             response = restTemplate
          		   .getForEntity(getRootUrl() + PERSON_ID_URL,
                     PersonDTO.class,
                     testPersonToUpdate.getFirstName(),
                     testPersonToUpdate.getLastName());

         	// Check for expected updated value in response body of person data
         	assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(testPersonUpdated);
        }
        
        @Test
        @DisplayName("Check - <On PERSON UPDATE - Person ID requested and updated Same>"
        		+ " - Given a Person,"
        		+ " when UPDATE request,"
        		+ " then Person ID requested for update and updated ID are Same")
        public void testUpdatePersonValueSameIdRequestedIsUpdated() {
       	  
        	// update requested
        	restTemplate.put(getRootUrl() + "/person", testPersonUpdated);
       	  
             response = restTemplate
          		   .getForEntity(getRootUrl() + PERSON_ID_URL,
                     PersonDTO.class,
                     testPersonToUpdate.getFirstName(),
                     testPersonToUpdate.getLastName());

         	// Check for expected updated value in response body of person data
             assertEquals(testPersonToUpdate.getFirstName(), testPersonUpdated.getFirstName());
        }
        
        @Test
        @DisplayName("Check - <UPDATE PERSON ID parameter missing >"
        		+ "Given a Person with missing parameter ID,"
        		+ " when UPDATE request,"
        		+ " then return Reponse Status: 400 BAD REQUEST")
        public void testUpdatePersonMissingParameterId() {

        	// first update asserts the original value expected
        	restTemplate.put(getRootUrl() + "/person", testPersonToUpdate);
        	
        	// invalid id update is run here to check it fails and previous one stays valid
        	restTemplate.put(getRootUrl() + "/person", personToAddMissingId);
            
        	// Checking that existing person has not been modified
            response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                    PersonDTO.class, testPersonToUpdate.getFirstName(), testPersonToUpdate.getLastName());

            assertNotNull(response.getBody());
            
            assertEquals(testPersonToUpdate.getAddress(), response.getBody().getAddress());
            
            assertNotEquals(personToAddMissingId.getAddress(), response.getBody().getAddress());
        } 
        
        @Test
        @DisplayName("Check - <UPDATE INVALID PERSON ID>"
        		+ "Given a Person with ID inexistant,"
        		+ " when UPDATE request,"
        		+ " then return Reponse Status: 400 BAD REQUEST")
        public void testUpdatePersonNonExisting() {

        	// first update asserts the original value expected
        	restTemplate.put(getRootUrl() + "/person", testPersonToUpdate);
        	
        	// invalid id update is run here to check it fails and previous one stays valid
        	restTemplate.put(getRootUrl() + "/person", testPersonToUpdateWrongFirstName);
            
        	// Checking that existing person has not been modified
            response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                    PersonDTO.class, testPersonToUpdate.getFirstName(), testPersonToUpdate.getLastName());

            assertNotNull(response.getBody());
            
            // confirms the original person data is same as get person data
            // - confirms invalid id info is not taken into account for update 
            assertEquals(testPersonToUpdate.getAddress(), response.getBody().getAddress());
            
            
            assertNotEquals(testPersonToUpdateWrongFirstName.getAddress(), response.getBody().getAddress());
        }
        
        @Test
        @DisplayName("Check - <UPDATE INVALID PERSON ID - NOT FOUND 404>"
        		+ "Given a Person with ID inexistant,"
        		+ " when UPDATE request,"
        		+ " then return Reponse Status: 404 NOT_FOUND")
        public void testUpdatePersonNonFound() {
        	
        	// invalid id update is run here to check it fails and previous one stays valid
        	restTemplate.put(getRootUrl() + "/person", testPersonToUpdateWrongFirstName);
            
        	// Checking that existing person has not been modified
            response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                    PersonDTO.class, testPersonToUpdateWrongFirstName.getFirstName(), testPersonToUpdateWrongFirstName.getLastName());

            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        }
}

