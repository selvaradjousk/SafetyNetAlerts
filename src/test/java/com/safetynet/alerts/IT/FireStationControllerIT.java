package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.FireStationDTO;

@DisplayName("FIRESTATION - Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FireStationControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private final static String FIRESTATION_ID_URL = "/firestation?station={station}&address={address}";

    private ObjectMapper objectMapper;
    
    FireStationDTO fireStationToAdd, fireStationToAddMissingAddress, fireStationToAddMissingId,
    fireStationToAddMissingBothIdAndAddress, fireStationToUpdate, fireStationUpdated,
    fireStationToUpdateWrongAddress, fireStationToUpdateWrongId;
    
    ResponseEntity<FireStationDTO> response, responseOnPost;
    ResponseEntity<FireStationDTO> getFireStationAdded;
    
    @BeforeEach
  public void setUp() {
    	objectMapper = new ObjectMapper();
    	
    	fireStationToAdd = new FireStationDTO().builder()
    			.stationId(3)
    			.address("addressAdded")
    			.build();
    	
        fireStationToAddMissingAddress = 
        		new FireStationDTO().builder()
    			.stationId(3)
    			.address("")
    			.build();
        
        
        fireStationToAddMissingId = 
        		new FireStationDTO().builder()
    			.stationId(3)
    			.address("")
    			.build();
        
        fireStationToAddMissingBothIdAndAddress = 
        		new FireStationDTO().builder()
    			.stationId(3)
    			.address("")
    			.build();
        
    	fireStationToUpdate = new FireStationDTO().builder()
    			.stationId(3)
    			.address("addressAddedtoUpdate")
    			.build();
    	
    	fireStationUpdated = new FireStationDTO().builder()
    			.stationId(5)
    			.address("addressAddedtoUpdate")
    			.build();
    	
    	fireStationToUpdateWrongAddress = new FireStationDTO().builder()
    			.stationId(3)
    			.address("qhdkfhqdkshfqhfdkqhdf")
    			.build();
    	
    	fireStationToUpdateWrongId = new FireStationDTO().builder()
    			.stationId(10)
    			.address("addressToUpdate")
    			.build();      
        
    	
     }
        
    
    // *********************************************************************************** 
    @DisplayName("IT - GET FIRESTATION")
    @Nested
    class GetFireStationIT {  
    	
        @BeforeEach
        public void init() {
     	   restTemplate
	   		.postForEntity(
	   				getRootUrl() + "/firestation",
	   				fireStationToAdd,
	   				FireStationDTO.class);
     	   
           response = restTemplate
        		   .getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                   FireStationDTO.class,
                   fireStationToAdd.getStationId(),
                   fireStationToAdd.getAddress());
           
           fireStationToAddMissingAddress = 
           		new FireStationDTO().builder()
       			.stationId(3)
       			.address("")
       			.build();
           
           fireStationToAddMissingId = 
           		new FireStationDTO().builder()
       			.stationId(3)
       			.address("")
       			.build();
           
           fireStationToAddMissingBothIdAndAddress = 
           		new FireStationDTO().builder()
       			.stationId(3)
       			.address("")
       			.build();
           
       	fireStationToUpdate = new FireStationDTO().builder()
    			.stationId(3)
    			.address("addressAddedtoUpdate")
    			.build();
    	
    	fireStationUpdated = new FireStationDTO().builder()
    			.stationId(5)
    			.address("addressAddedtoUpdate")
    			.build();
    	
    	fireStationToUpdateWrongAddress = new FireStationDTO().builder()
    			.stationId(3)
    			.address("qhdkfhqdkshfqhfdkqhdf")
    			.build();
    	
    	fireStationToUpdateWrongId = new FireStationDTO().builder()
    			.stationId(10)
    			.address("addressToUpdate")
    			.build();        
           
           
           
           
        }
        
        @AfterEach
        public void finish() {
                 
            restTemplate.delete(getRootUrl()
            		+ FIRESTATION_ID_URL,
            		fireStationToAdd.getStationId(),
            		fireStationToAdd.getAddress()); 
        }
        
        
        
        @Test
        @DisplayName("Check - <RESPONSE NOT NULL>"
        		+ " - Given a firestation,"
        		+ " when GET request,"
        		+ " then firestation data returned is not null")
        public void testGetFireStationnRequestWithValidFireStationResponseNotNull() {

           assertNotNull(response);
        }
        
        @Test
        @DisplayName("Check - <RESPONSE HEADER NOT NULL>"
        		+ " - Given a FireStation,"
        		+ " when GET request,"
        		+ " then response header not null")
        public void testGetFireStationRequestWithValidFireStationResponseHeaderNotNull() {
                assertNotNull(response.getHeaders());
                assertEquals("application/json", (response.getHeaders().getContentType()).toString());
        }    
       
        @Test
        @DisplayName("Check - <RESPONSE BODY NOT NULL>"
        		+ " - Given a FireStation,"
        		+ " when GET request,"
        		+ " then response body is not Null")
        public void testGetFireStationRequestWithValidResponseBodyNotNull() {
            assertNotNull(response.getBody());
        }       
        
        @Test
        @DisplayName("Check - <NOT NULL - StationId, Address Values>"
        		+ " - Given a FireStation,"
        		+ " when GET request,"
        		+ " then return response body StationId & Address param value not null")
        public void testGetFireStationRequestWithValidFireStationParamValueNotNull() {
            
            assertNotNull(response.getBody().getStationId());
            assertNotNull(response.getBody().getAddress());
        } 
        
        @Test
        @DisplayName("Check - Response Status<200 OK>"
        		+ " - Given a FireStation,"
        		+ " when GET request,"
        		+ " then return Status: 200 OK")
        public void testGetFireStationRequestWithValidFireStationResponseStatusOk() {
            assertEquals((HttpStatus.OK), response.getStatusCode());
            assertEquals(200, response.getStatusCodeValue());
            assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
       }  
        
        @Test
        @DisplayName("Check - <VALIDATE FIELDS EQUAL>"
        		+ " - Given a FireStation,"
        		+ " when GET request,"
        		+ " then return response fields equals expected added FireStation")
        public void testGetFireStationRequestWithValidPersonThenSimilarToAddedFireStationValues() {
            assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(fireStationToAdd);
        }
        
        
        @Test
        @DisplayName("Check - <MISSING FIRESTATION ID>"
        		+ "Given a FireStation with missing ID,"
        		+ " when GET request,"
        		+ " then return Reponse Status: 4xx BAD REQUEST")
        public void testGetPersonMissingId() {
        	
            restTemplate
            .postForEntity(getRootUrl() + "/firestation",
            		fireStationToAddMissingId,
            		FireStationDTO.class);
            
        	response = restTemplate
        			.getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                    FireStationDTO.class,
                    fireStationToAddMissingId.getStationId(),
                    fireStationToAddMissingId.getAddress());
        	
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
            assertEquals(0, response.getBody().getStationId());
            
        }
        
        @Test
        @DisplayName("Check - <MISSING FIRESTATION Address>"
        		+ "Given a FireStation with missing Address,"
        		+ " when GET request,"
        		+ " then return Reponse Status: 4xx BAD REQUEST")
        public void testGetPersonMissingAddress() {
        	
            restTemplate
            .postForEntity(getRootUrl() + "/firestation",
            		fireStationToAddMissingAddress,
            		FireStationDTO.class);
            
        	response = restTemplate
        			.getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                    FireStationDTO.class,
                    fireStationToAddMissingAddress.getStationId(),
                    fireStationToAddMissingAddress.getAddress());
        	
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
            assertEquals(0, response.getBody().getStationId());
            
        }
        
        @Test
        @DisplayName("Check - <MISSING FIRESTATION Both Params"
        		+ "Given a FireStation with missing Both Params,"
        		+ " when GET request,"
        		+ " then return Reponse Status: 4xx BAD REQUEST")
        public void testGetPersonMissingBothParams() {
        	
            restTemplate
            .postForEntity(getRootUrl() + "/firestation",
            		fireStationToAddMissingAddress,
            		FireStationDTO.class);
            
        	response = restTemplate
        			.getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                    FireStationDTO.class,
                    fireStationToAddMissingBothIdAndAddress.getStationId(),
                    fireStationToAddMissingBothIdAndAddress.getAddress());
        	
            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
            assertEquals(0, response.getBody().getStationId());
            
        }
        
        

        @Test
        @DisplayName("Check - <INVALID PERSON ID - 409>"
        		+ "Given a Person with invalid ID,"
        		+ " when GET request,"
        		+ " then return Reponse Status: 409 NOT FOUND REQUEST")
        public void testGetPersonForInvalidID() {
        	
       
        	response = restTemplate
        			.getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                    FireStationDTO.class,
                    99,
                    "sdqfsqfdqsf");
        	
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
            assertEquals(0, response.getBody().getStationId());
            
        }

    }
    

    // ***********************************************************************************
    
    
    @DisplayName("IT - ADD FIRESTATION")
    @Nested
    class AddNewFireStationIT {  
    	
        @BeforeEach
        public void init() {
      	
        }
        
        @AfterEach
        public void finish() {
                 
        	restTemplate.delete(getRootUrl()
        			+ FIRESTATION_ID_URL,
        			fireStationToAdd.getStationId(),
        			fireStationToAdd.getAddress());
        }
    
    @Test
    @DisplayName("Check - <RESPONSE NOT NULL>"
    		+ " - Given a FireStation to add,"
    		+ " when POST request,"
    		+ " then FireStation added is not null")
    public void testAddFireStationRequestWithValidFireStationResponseNotNull() {
    	 
    	response = restTemplate
    			.postForEntity(getRootUrl() + "/firestation",
    					fireStationToAdd,
    					FireStationDTO.class);
    	
        assertNotNull(response);
    }
    
    
    @Test
    @DisplayName("Check - <RESPONSE HEADER NOT NULL>"
    		+ " - Given a FireStation to add,"
    		+ " when POST request,"
    		+ " then response header not null")
    public void testAddFireStationRequestWithValidFiresStationResponseHeaderNotNull() {
            response = restTemplate
        			.postForEntity(getRootUrl() + "/firestation",
        					fireStationToAdd,
        					FireStationDTO.class);
            //When Post created CHECKs
            assertNotNull(response.getHeaders());
            assertEquals("application/json", (response.getHeaders().getContentType()).toString());
    } 
    
    @Test
    @DisplayName("Check - <RESPONSE BODY NOT NULL>"
    		+ " - Given a FireStation to add,"
    		+ " when POST request,"
    		+ " then response Body not null")
    public void testAddFireStationRequestWithValidFiresStationResponseBodyNotNull() {
            response = restTemplate
        			.postForEntity(getRootUrl() + "/firestation",
        					fireStationToAdd,
        					FireStationDTO.class);
            //When Post created CHECKs
            assertNotNull(response.getBody());
            
    }
    
    
    @Test
    @DisplayName("Check - <NOT NULL - Id and address Value>"
    		+ " - Given a FireStation to add,"
    		+ " when POST request,"
    		+ " then return response body StationId param value not null")
    public void testAddFireStationRequestWithValidFireStationParamValueNotNull() {
            response = restTemplate
        			.postForEntity(getRootUrl() + "/firestation",
        					fireStationToAdd,
        					FireStationDTO.class);
      //When Post created CHECKs
        assertNotNull(response.getBody().getStationId());
        assertNotNull(response.getBody().getAddress());
    }
    
   
    @Test
    @DisplayName("Check - Response Status<201 CREATED>"
    		+ " - Given a FireStation to add,"
    		+ " when POST request,"
    		+ " then return Status: 201 Created")
    public void testAddFireStationRequestWithValidFireStationResponseStatusCreated() {
    	 
    	response = restTemplate
    			.postForEntity(getRootUrl() + "/firestation",
    					fireStationToAdd,
    					FireStationDTO.class);

        //When Post created CHECKs
        assertEquals((HttpStatus.CREATED), response.getStatusCode());

    }
    
    
    @Test
    @DisplayName("Check - <VALIDATE FIELDS EQUAL>"
    		+ " - Given a FireStation to add,"
    		+ " when POST request,"
    		+ " then return response fields equals added FireStation")
    public void testAddFireStationRequestWithValidFireStationThenSimilarToAddedFireStationValues() {
        response = restTemplate
           		.postForEntity(
           				getRootUrl() + "/firestation",
    					fireStationToAdd,
    					FireStationDTO.class);
      //When Post created CHECKs
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(fireStationToAdd);
    }
    
    
    @Test
    @DisplayName("Check - able to <Retrieve ADDED FIRESTATION DATA>"
    		+ " - Given a FireStation to add,"
    		+ " when POST request, followed by GET FireStation"
    		+ " then return FireStation Added Before successfully")
    public void testAddFireStationRequestWithValidFireStationThenOnGetRequestFireStationAddedREturnedSuccessfully() {
            response = restTemplate
               		.postForEntity(
               				getRootUrl() + "/firestation",
               				fireStationToAdd,
               				FireStationDTO.class);
            
            getFireStationAdded = restTemplate.getForEntity(getRootUrl()
            		+ FIRESTATION_ID_URL,
                    FireStationDTO.class,
                    fireStationToAdd.getStationId(),
                    fireStationToAdd.getAddress());
            
            
    	//Then FireStation can be retrieved CHECKs
        assertTrue(getFireStationAdded.getStatusCode().is2xxSuccessful());   
            
            assertEquals((HttpStatus.OK), getFireStationAdded.getStatusCode());
            assertEquals(200, getFireStationAdded.getStatusCodeValue());
            assertEquals(HttpStatus.OK.value(), getFireStationAdded.getStatusCodeValue());
    } 
    
    @Test
    @DisplayName("Check - <CONFLICT STATUS - ADD EXISTING FIRESTATION>"
    		+ "Given a registered FireStation,"
    		+ " when POST request,"
    		+ " then return Response Status: 409 CONFLICT" +
            "should be returned")
    public void testAddFireStationRequestExistingPerson() {

        restTemplate.postForEntity(
        		getRootUrl() + "/firestation",
        		fireStationToAdd,
        		FireStationDTO.class);
        
        response = restTemplate
           		.postForEntity(
           				getRootUrl() + "/firestation",
           				fireStationToAdd,
           				FireStationDTO.class);

        assertThat(response.getBody()).isNotSameAs(fireStationToAdd);
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCodeValue());

    }
    
    @Test
    @DisplayName("Check - <MISSING FIRESTATION ID>"
    		+ "Given a FireStation with missing ID,"
    		+ " when POST request,"
    		+ " then return Reponse Status: 4xx BAD REQUEST")
    public void testAddFireStationMissingId() {

        response = restTemplate
        		.postForEntity(
        				getRootUrl() + "/firestation",
        				fireStationToAddMissingId,
        				FireStationDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(0, response.getBody().getStationId());
        assertNull(response.getBody().getAddress());
               
    }
    
    
    @Test
    @DisplayName("Check - <MISSING FIRESTATION ADDRESS>"
    		+ "Given a FireStation with missing ADDRESS,"
    		+ " when POST request,"
    		+ " then return Reponse Status: 4xx BAD REQUEST")
    public void testAddFireStationMissingAddress() {

        response = restTemplate
        		.postForEntity(
        				getRootUrl() + "/firestation",
        				fireStationToAddMissingAddress,
        				FireStationDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(0, response.getBody().getStationId());
        assertNull(response.getBody().getAddress());
               
    }
    
    
    @Test
    @DisplayName("Check - <MISSING FIRESTATION ID & ADDRESS>"
    		+ "Given a FireStation with missing Id & ADDRESS,"
    		+ " when POST request,"
    		+ " then return Reponse Status: 4xx BAD REQUEST")
    public void testAddFireStationMissingBothIdAndAddress() {

        response = restTemplate
        		.postForEntity(
        				getRootUrl() + "/firestation",
        				fireStationToAddMissingBothIdAndAddress,
        				FireStationDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(0, response.getBody().getStationId());
        assertNull(response.getBody().getAddress());
               
    }

    }
   
    
    // *********************************************************************************** 
    @DisplayName("IT - UPDATE FIRESTATION")
    @Nested
    class UpdateFireStationIT {  
    	
        @BeforeEach
        public void init() {
        	
        	// person created
     	   responseOnPost = restTemplate
	   		.postForEntity(
	   				getRootUrl() + "/firestation",
	   				fireStationToUpdate,
	   				FireStationDTO.class);

     	   
     	  
      	// update requested
      	restTemplate.put(getRootUrl() + "/firestation", fireStationUpdated);
     	  
           response = restTemplate
        		   .getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                   FireStationDTO.class,
                   fireStationToUpdate.getStationId(),
                   fireStationToUpdate.getAddress());
           

        } 
        
        @AfterEach
        public void finish() {
                 
            restTemplate.delete(getRootUrl() + FIRESTATION_ID_URL,
            		fireStationToUpdate.getStationId(),
            		fireStationToUpdate.getAddress()); 
            
            restTemplate.delete(getRootUrl() + FIRESTATION_ID_URL,
            		fireStationUpdated.getStationId(),
            		fireStationUpdated.getAddress());
        }
        
        @Test
        @DisplayName("Check - <Station DATA EXIST BEFORE UPDATE>"
        		+ " - Given a FireStation,"
        		+ " when UPDATE request,"
        		+ " then FireStation data EXIST BEFORE UPDATE")
        public void testUpdateFireStationRequestWithValidFireStationExisitBeforeUpdate() {
      	   
           	// Person Data Exist
           	assertNotNull(responseOnPost);
           	assertNotNull(responseOnPost.getBody().getAddress());
        }   
    
    
        @Test
        @DisplayName("Check - <UPDATE - STATUS 200 OK>"
        		+ " - Given a FireStation,"
        		+ " when UPDATE request,"
        		+ " then return response Status: 200 OK")
        public void testUpdateFireStationResponseStatusOk() {
      	   
           	// FireStation Update Data Exist
        	assertEquals((HttpStatus.OK), response.getStatusCode());
        	assertEquals(200, response.getStatusCodeValue());
        	assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        }
        
       
        @Test
        @DisplayName("Check - <UPDATE - RESPONSE NOT NULL>"
        		+ " - Given a FireStation,"
        		+ " when UPDATE request,"
        		+ " then returned response Not Null")
        public void testUpdateFireStationResponseResponseNotNull() {
      	   
            // FireStation Update data updated not null
            assertNotNull(response);
            assertNotNull(response.getHeaders());
            assertNotNull(response.getBody());
        }
        
        
        @Test
        @DisplayName("Check - <On UPDATE - Value Changed>"
        		+ " - Given a FireStation,"
        		+ " when UPDATE request,"
        		+ " then returned response Value Not Same Before Update")
        public void testUpdateFireStationValueBeforeAfterNotSame() {
           	// Value of Address parameter stored for reference before update
       	  int valueBeforeUpdate = responseOnPost.getBody().getStationId();
       	  
        	// update requested
        	restTemplate.put(getRootUrl() + "/firestation", fireStationUpdated);
       	  
             response = restTemplate
          		   .getForEntity(getRootUrl() + FIRESTATION_ID_URL,
          				 FireStationDTO.class,
                     fireStationToUpdate.getStationId(),
                     fireStationToUpdate.getAddress());
             
             int valueAfterUpdate = response.getBody().getStationId();
            // Check value before and after update are not equal
        	assertNotEquals(valueBeforeUpdate, valueAfterUpdate);
        } 
        
        
        
    
    }
    
}
