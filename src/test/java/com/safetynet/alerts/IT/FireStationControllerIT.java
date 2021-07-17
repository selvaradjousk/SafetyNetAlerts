package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    fireStationToAddMissingBothIdAndAddress;
    
    ResponseEntity<FireStationDTO> response;
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
        }
        
        @AfterEach
        public void finish() {
                 
            restTemplate.delete(getRootUrl() + FIRESTATION_ID_URL, fireStationToAdd.getStationId(), fireStationToAdd.getAddress()); 
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
                 
        	restTemplate.delete(getRootUrl() + FIRESTATION_ID_URL, fireStationToAdd.getStationId(), fireStationToAdd.getAddress());
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
    
}
