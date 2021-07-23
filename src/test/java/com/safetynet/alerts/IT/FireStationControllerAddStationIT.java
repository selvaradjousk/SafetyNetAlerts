package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

@DisplayName("FIRESTATION ADD ENDPOINT- Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FireStationControllerAddStationIT {

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
    fireStationToAddMissingBothIdAndAddress;
    
    ResponseEntity<FireStationDTO> response, responseOnPost;
    ResponseEntity<FireStationDTO> getFireStationAdded;
   	
        @BeforeEach
        public void init() {
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
