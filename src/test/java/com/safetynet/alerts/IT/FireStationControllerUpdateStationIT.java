package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

@DisplayName("FIRESTATION UPDATE ENDPOINT - Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FireStationControllerUpdateStationIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private final static String FIRESTATION_ID_URL = "/firestation?station={station}&address={address}";

    private ObjectMapper objectMapper;
    
    FireStationDTO fireStationToAddMissingId,
    fireStationToAddMissingBothIdAndAddress, fireStationToUpdate, fireStationUpdated,
    fireStationToUpdateWrongAddress, fireStationToUpdateWrongId;
    
    ResponseEntity<FireStationDTO> response, responseOnPost;
    ResponseEntity<FireStationDTO> getFireStationAdded;
    
    @BeforeEach
  public void setUp() {
    	objectMapper = new ObjectMapper();
     
        
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
        
        
        @Test
        @DisplayName("Check - <On FIRESTATION UPDATE - Value expected Updated>"
        		+ " - Given a FireStation,"
        		+ " when UPDATE request,"
        		+ " then FireStation data updated with expected value")
        public void testUpdateFireStationValueSameAsExpectedUpdateValue() {
       	  
        	// update requested
        	restTemplate.put(getRootUrl() + "/firestation", fireStationUpdated);
       	  
             response = restTemplate
          		   .getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                     FireStationDTO.class,
                     fireStationToUpdate.getStationId(),
                     fireStationToUpdate.getAddress());

         	// Check for expected updated value in response body of person data
         	assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(fireStationUpdated);
        }
        
        
        @Test
        @DisplayName("Check - <On FIRESTATION UPDATE - FireStation ID requested and updated Same>"
        		+ " - Given a FireStation,"
        		+ " when UPDATE request,"
        		+ " then FireStation ID requested for update and updated ID are Same")
        public void testUpdateFireStationValueSameIdRequestedIsUpdated() {
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
         	// Check for expected updated value in response body of person data
             assertEquals(valueAfterUpdate, fireStationUpdated.getStationId(), "Expected value is 5");
        }
        
        @Test
        @DisplayName("Check - <UPDATE FIRESTATION ID parameter missing >"
        		+ "Given a FireStation with missing parameter ID,"
        		+ " when UPDATE request,"
        		+ " then return Reponse Status: 400 BAD REQUEST")
        public void testUpdateFireStationMissingParameterId() {

        	// first update asserts the original value expected
        	restTemplate.put(getRootUrl() + "/firestation", fireStationToUpdate);
        	
        	// invalid id update is run here to check it fails and previous one stays valid
        	restTemplate.put(getRootUrl() + "/firestation", fireStationToAddMissingId);
            
        	// Checking that existing person has not been modified
            response = restTemplate.getForEntity("http://localhost:" + port + FIRESTATION_ID_URL,
                    FireStationDTO.class, fireStationToUpdate.getStationId(), fireStationToUpdate.getAddress());

            assertNotNull(response.getBody());
            
            assertEquals(fireStationToUpdate.getAddress(), response.getBody().getAddress());
            
            assertNotEquals(fireStationToAddMissingId.getAddress(), response.getBody().getAddress());
        } 
        
        
        @Test
        @DisplayName("Check - <UPDATE INVALID FIRESTATION ADDRESS>"
        		+ "Given a FIRESTATION with ADDRESS inexistant,"
        		+ " when UPDATE request,"
        		+ " then return Reponse Status: 400 BAD REQUEST")
        public void testUpdateFireStationNonExisting() {

        	// first update asserts the original value expected
        	restTemplate.put(getRootUrl() + "/firestation", fireStationToUpdate);
        	
        	// invalid id update is run here to check it fails and previous one stays valid
        	restTemplate.put(getRootUrl() + "/firestation", fireStationToUpdateWrongId);
            
        	// Checking that existing person has not been modified
            response = restTemplate.getForEntity("http://localhost:" + port + FIRESTATION_ID_URL,
                    FireStationDTO.class, fireStationToUpdate.getStationId(), fireStationToUpdate.getAddress());

            assertNotNull(response.getBody());
            
            // confirms the original person data is same as get person data
            // - confirms invalid id info is not taken into account for update 
            assertEquals(fireStationToUpdate.getAddress(), response.getBody().getAddress());
            
            
            assertNotEquals(fireStationToUpdateWrongId.getAddress(), response.getBody().getAddress());
        }
   
  
   
}
