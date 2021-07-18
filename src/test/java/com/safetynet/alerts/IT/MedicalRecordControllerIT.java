package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

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
import com.safetynet.alerts.dto.MedicalRecordDTO;

@DisplayName("MedicalRecord Controller - Integration Tests")
	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class MedicalRecordControllerIT {

	    @Autowired
	    private TestRestTemplate restTemplate;

	    @LocalServerPort
	    private int port;

	    private final static String MEDICALRECORD_ID_URL = "/medicalRecord?firstName={firstName}&lastName={lastName}";

	    private String getRootUrl() {
	        return "http://localhost:" + port;
	    }

	    private ObjectMapper objectMapper;
	    
	    MedicalRecordDTO medicalRecordToGet,  medicalRecordGetUnknown,
	    medicalRecordToAdd;
	    
	    ResponseEntity<MedicalRecordDTO> response;
	    
	    @BeforeEach
	  public void setUp() {
	        medicalRecordToGet = new MedicalRecordDTO(
	        		"Test GET First Name",
	        		"Test GET Last Name",
	                "01/01/1970",
	                Arrays.asList("Test Medication1"),
	                Arrays.asList("Test Allergy1"));
	        
	        medicalRecordGetUnknown = new MedicalRecordDTO(
	        		"UNKNOWN NAME",
	        		"UNKNOWN NAME",
	        		"01/01/1970",
	                Arrays.asList("Test Medication1"),
	                Arrays.asList("Test Allergy1"));
	        
	        medicalRecordToAdd = new MedicalRecordDTO(
	        		"firstNameAdd",
	        		"lastNameAdd",
	                "03/06/1991",
	                Arrays.asList("Test Medication1"),
	                Arrays.asList("Test Allergy1"));
	        
	    }
	    
	    //*************************************************************************************************
	    
	    
	    @DisplayName("IT - GET MEDICAL RECORD")
	    @Nested
	    class GetMedicalRecordIT {  
	    	
	        @BeforeEach
	        public void init() {
	        	 restTemplate.postForEntity(getRootUrl()
	        			 + "/medicalRecord",
	        			 medicalRecordToGet,
	        			 MedicalRecordDTO.class);
	        }
	        @AfterEach
	        public void finish() {

	        }

	        
	        @Test
	        @DisplayName("Check (Valid Input ID Response not null)"
	        		+ " - Given valid ID param,"
	        		+ " when GET request,"
	        		+ " then response not null")
	        public void testGetRequestForValidIdInputResponseBodyNotNull() {

	        	response = restTemplate
	        			.getForEntity(getRootUrl() + 
	        					MEDICALRECORD_ID_URL,
	        					MedicalRecordDTO.class,
	        					medicalRecordToGet.getFirstName(),
	        					medicalRecordToGet.getLastName());

	        	 assertNotNull(response);
	        	 assertNotNull(response.getBody());
	        	 assertNotNull(response.getHeaders());
	        }
	        
	        
	        @Test
	        @DisplayName("Check (Valid Input ID - Response status 200 OK)"
	        		+ " - Given valid ID param,"
	        		+ " when GET request,"
	        		+ " then Response status 200 OK")
	        public void testGetRequestForValidIdInputResponseStatus() {

	        	response = restTemplate
	        			.getForEntity(getRootUrl() + 
	        					MEDICALRECORD_ID_URL,
	        					MedicalRecordDTO.class,
	        					medicalRecordToGet.getFirstName(),
	        					medicalRecordToGet.getLastName());

	            assertEquals( HttpStatus.OK.value(), response.getStatusCodeValue());
	        }
	        
	       
	        @Test
	        @DisplayName("Check (Valid Input ID expected returned)"
	        		+ " - Given valid ID param,"
	        		+ " when GET request,"
	        		+ " then expected MedicalRecord returned")
	        public void testGetRequestForValidIdInput() {

	            response = restTemplate
	            		.getForEntity(getRootUrl() +
	                    MEDICALRECORD_ID_URL,
	                    MedicalRecordDTO.class,
	                    medicalRecordToGet.getFirstName(),
	                    medicalRecordToGet.getLastName());

	            assertThat(response.getBody())
	            .usingRecursiveComparison().isEqualTo(medicalRecordToGet);
	        }
	        
	       
	        
	        @Test
	        @DisplayName("Check (Invalid input - no firstname)"
	        		+ " - Given incomplete ID param no firstname,"
	        		+ " when GET request,"
	        		+ " then BAD REQUEST status should be returned")
	        public void testGetRequestForInputIdNoFirstName() {

	            // get request with missing firstName
	            response = restTemplate
	            		.getForEntity(getRootUrl() +
	                    MEDICALRECORD_ID_URL,
	                    MedicalRecordDTO.class,
	                    "",
	                    medicalRecordToGet.getLastName());

	            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	        }
	        
	        
	        @Test
	        @DisplayName("Check (Invalid input - no lastname)"
	        		+ " - Given incomplete ID param no lastname,"
	        		+ " when GET request,"
	        		+ " then BAD REQUEST status should be returned")
	        public void testGetRequestForInputIdNoLastName() {

	        	// get request with missing lastName
	            response = restTemplate
	            		.getForEntity(getRootUrl() +
	                    MEDICALRECORD_ID_URL,
	                    MedicalRecordDTO.class,
	                    medicalRecordToGet.getFirstName(),
	                    "");

	            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	        }
	        
	      
	        
	        @Test
	        @DisplayName("Check (Invalid input - no input)"
	        		+ " - Given incomplete ID param no input,"
	        		+ " when GET request,"
	        		+ " then BAD REQUEST status should be returned")
	        public void testGetRequestForInputIdNoInput() {

	            // get request with missing input
	            response = restTemplate
	            		.getForEntity(getRootUrl() +
	                    MEDICALRECORD_ID_URL,
	                    MedicalRecordDTO.class,
	                    "",
	                    "");

	            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	        }
	        
	        
	        @Test
	        @DisplayName("Check (non existing medical record)"
	        		+ " - Given MedicalRecord does not exist,"
	        		+ " when GET request,"
	        		+ " then NOT FOUND should be returned")
	        public void testGetRequestForNonExistingRecord() {

	            response = restTemplate
	            		.getForEntity(getRootUrl() +
	                    MEDICALRECORD_ID_URL,
	                    MedicalRecordDTO.class,
	                    medicalRecordGetUnknown.getFirstName(),
	                    medicalRecordGetUnknown.getLastName());

	            assertEquals( HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	        }  
	        
	    }
	    
	    
	    //*************************************************************************************************
	    
	    
	    @DisplayName("IT - ADD MEDICAL RECORD")
	    @Nested
	    class AddMedicalRecordIT {  
	    	
	        @BeforeEach
	        public void init() {

	        }
	        @AfterEach
	        public void finish() {
	            restTemplate.delete(getRootUrl()
	            		+ MEDICALRECORD_ID_URL,
	            		medicalRecordToAdd.getFirstName(),
	            		medicalRecordToAdd.getLastName());
  
	        }
	        
	        @Test
	        @DisplayName("Check (Valid input Response Not NULL)"
	        		+ " - Given a MedicalRecord,"
	        		+ " when POST request,"
	        		+ " then response not null")
	        public void testAddMedicalRecordValidInputResponseNotNull() {

	            response = restTemplate
	            		.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAdd,
	                    MedicalRecordDTO.class);

	            assertNotNull(response);
	        }
	        
	        
	        @Test
	        @DisplayName("Check (Valid input Response body Not NULL)"
	        		+ " - Given a MedicalRecord,"
	        		+ " when POST request,"
	        		+ " then response body not null")
	        public void testAddMedicalRecordValidInputResponseBodyNotNull() {

	            response = restTemplate
	            		.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAdd,
	                    MedicalRecordDTO.class);

	            assertNotNull(response.getBody());
	            assertNotNull(response.getHeaders());
	        }
	        
	        
	        @Test
	        @DisplayName("Check (Valid input Response status 201 CREATED)"
	        		+ " - Given a MedicalRecord,"
	        		+ " when POST request,"
	        		+ " then response status 201 CREATED")
	        public void testAddMedicalRecordValidInputResponseStatusCreated() {

	            response = restTemplate
	            		.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAdd,
	                    MedicalRecordDTO.class);

	            assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
	        }
	        
	        
	        @Test
	        @DisplayName("Check (Valid input New record equals expected)"
	        		+ " - Given a MedicalRecord,"
	        		+ " when POST request,"
	        		+ " then MedicalRecord added match expected")
	        public void testAddMedicalRecordValidInputMatchExpected() {

	            response = restTemplate
	            		.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAdd,
	                    MedicalRecordDTO.class);
	         
	            assertThat(response.getBody())
	                    .usingRecursiveComparison().isEqualTo(medicalRecordToAdd);
	        } 
	        
	    }

}
