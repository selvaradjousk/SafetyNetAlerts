package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
    
    PersonDTO testPersonToBeAdded, testPersonToUpdate, personToAddMissingId,
    testPersonUpdated, testPersonToUpdateWrongFirstName, testPersonToBeDeleted;
    ResponseEntity<PersonDTO> response, responseOnPost;
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
        
        testPersonToBeDeleted = new PersonDTO().builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.address("Testing Street")
        		.city("TestCity")
        		.zip(98765)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();       
        
 
    	
    }
    
    // *********************************************************************************** 
    @DisplayName("IT - GET PERSON")
    @Nested
    class GetPersonIT {  
    	
        @BeforeEach
        public void init() {
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
 
    // ***********************************************************************************
    
    @DisplayName("IT - ADD NEW PERSON")
    @Nested
    class AddNewPersonIT {  
    	
        @BeforeEach
        public void init() {
 
        }
        
        @AfterEach
        public void finish() {
                 
            restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToBeAdded.getFirstName(), testPersonToBeAdded.getLastName()); 
        }
    

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
    
    
    // *********************************************************************************** 
    @DisplayName("IT - UPDATE PERSON")
    @Nested
    class UpdatePersonIT {  
    	
        @BeforeEach
        public void init() {
        	
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
        
       
        // ***********************************************************************************
        
        @DisplayName("IT - DELETE PERSON")
        @Nested
        class DeletePersonIT {  
        	
            @BeforeEach
            public void init() {
          	   restTemplate
     	   		.postForEntity(
     	   				getRootUrl() + "/person",
     	   			testPersonToBeDeleted,
     	   				PersonDTO.class);
          	   
                response = restTemplate
             		   .getForEntity(getRootUrl() + PERSON_ID_URL,
                        PersonDTO.class,
                        testPersonToBeDeleted.getFirstName(),
                        testPersonToBeDeleted.getLastName());
            }
            
            @AfterEach
            public void finish() {
                     
                restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToBeDeleted.getFirstName(), testPersonToBeDeleted.getLastName()); 
            }
            
        }
        
       
        @Test
        @DisplayName("Check - <RESPONSE GET after Delete 404 NOT FOUND>"
        		+ " - Given a Person,"
        		+ " when DELETE request,"
        		+ " then response on get is 404 NOT FOUND")
        public void testDeletePersonRequestWithValidPersonResponseNull() {

        	// Confirms person id found before delete
        	assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        	
        	// delete request executed
        	restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToBeDeleted.getFirstName(), testPersonToBeDeleted.getLastName());
        	
            // Checking that existing person has been deleted
            response = restTemplate.getForEntity(getRootUrl() + PERSON_ID_URL,
                    PersonDTO.class, testPersonToBeDeleted.getFirstName(), testPersonToBeDeleted.getLastName());
            
            // response is not null - has information
            assertNotNull(response);
            assertNotNull(response.getHeaders());
            assertNotNull(response.getBody());
            
            // person is not found - confirms delete process
            assertEquals(404, response.getStatusCodeValue());
            assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
       }
        
        
        
        
        
    }

}