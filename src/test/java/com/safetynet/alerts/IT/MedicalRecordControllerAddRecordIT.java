package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

@DisplayName("MedicalRecord ADD Controller - Integration Tests")
	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class MedicalRecordControllerAddRecordIT {

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
	    medicalRecordToAdd, medicalRecordToAddIdNoFirstName,
	    medicalRecordToAddIdNoLastName, medicalRecordToAddIdNoInput,
	    medicalRecordToAddIdNull;
	    
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
	        
	         medicalRecordToAddIdNoFirstName = new MedicalRecordDTO(
	        		  "",
	        		  "lastNameAdd",
	        		  "01/01/1970",
	        		  Arrays.asList("Test Medication1"),
	        		  Arrays.asList("Test Allergy1"));
	         
	         medicalRecordToAddIdNoLastName = new MedicalRecordDTO(
	       		  "firstNameAdd",
	       		  "",
	       		  "01/01/1970",
	       		  Arrays.asList("Test Medication1"),
	       		  Arrays.asList("Test Allergy1"));
	         
	         medicalRecordToAddIdNoInput = new MedicalRecordDTO(
	       		  "",
	       		  "",
	       		  "01/01/1970",
	       		  Arrays.asList("Test Medication1"),
	       		  Arrays.asList("Test Allergy1"));
	         
	         medicalRecordToAddIdNull = new MedicalRecordDTO(
	       		  null,
	       		  null,
	       		  "01/01/1970",
	       		  Arrays.asList("Test Medication1"),
	       		  Arrays.asList("Test Allergy1"));
	         
	        
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
	        
	        @Test
	        @DisplayName("Check (ID input No FirstName)"
	        		+ " - Given a MedicalRecord with ID no firstname,"
	        		+ " when POST request,"
	        		+ " then return BAD REQUEST status")
	        public void testMedicalRecordIdNoFirstName() {

	            response = restTemplate.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAddIdNoFirstName,
	                    MedicalRecordDTO.class);

	            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	        }
	        
	        @Test
	        @DisplayName("Check (ID input No LastName)"
	        		+ " - Given a MedicalRecord with ID no lastname,"
	        		+ " when POST request,"
	        		+ " then return BAD REQUEST status")
	        public void testMedicalRecordIdNoLastName() {

	            response = restTemplate.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAddIdNoLastName,
	                    MedicalRecordDTO.class);

	            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	        }
	        
	        @Test
	        @DisplayName("Check (ID input No input)"
	        		+ " - Given a MedicalRecord with ID no input,"
	        		+ " when POST request,"
	        		+ " then return BAD REQUEST status")
	        public void testMedicalRecordIdNoInput() {

	            response = restTemplate.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAddIdNoInput,
	                    MedicalRecordDTO.class);

	            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	        }
	        
	        @Test
	        @DisplayName("Check (ID input Null)"
	        		+ " - Given a MedicalRecord with ID null,"
	        		+ " when POST request,"
	        		+ " then return BAD REQUEST status")
	        public void testMedicalRecordIdNull() {

	            response = restTemplate.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAddIdNull,
	                    MedicalRecordDTO.class);

	            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	        }
	        
	        

	        @Test
	        @DisplayName("Check (Existing one for update - response not null)"
	        		+ " - Given a existing MedicalRecord,"
	        		+ " when POST request,"
	        		+ " then response not null ")
	        public void testAddRequestForExistingMedicalRecordResponseNotNull() {

	            restTemplate.postForEntity(getRootUrl()
	            		+ "/medicalRecord",
	            		medicalRecordToAdd,
	            		MedicalRecordDTO.class);
	            
	            response = restTemplate
	            		.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAdd,
	                    MedicalRecordDTO.class);

	            assertNotNull(response.getBody());
	            assertNotNull( medicalRecordToAdd);
	        }
	        
	        @Test
	        @DisplayName("Check (Existing one update - response no match original)"
	        		+ " - Given a existing MedicalRecord,"
	        		+ " when POST request,"
	        		+ " then response no match original")
	        public void testAddRequestForExistingMedicalRecordResponseNotSame() {

	            restTemplate.postForEntity(getRootUrl()
	            		+ "/medicalRecord",
	            		medicalRecordToAdd,
	            		MedicalRecordDTO.class);
	            
	            response = restTemplate
	            		.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAdd,
	                    MedicalRecordDTO.class);

	            assertNotEquals( medicalRecordToAdd, response.getBody());
	        }
	        
	        
	        @Test
	        @DisplayName("Check (Existing one update - values not same)"
	        		+ " - Given a existing MedicalRecord,"
	        		+ " when POST request,"
	        		+ " then response no match values to original")
	        public void testAddRequestForExistingMedicalRecordResponseValuesNotSame() {

	            restTemplate.postForEntity(getRootUrl()
	            		+ "/medicalRecord",
	            		medicalRecordToAdd,
	            		MedicalRecordDTO.class);
	            
	            response = restTemplate
	            		.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAdd,
	                    MedicalRecordDTO.class);

	            assertNotEquals( medicalRecordToAdd.getAllergies(), response.getBody().getAllergies());
	            assertNotEquals( medicalRecordToAdd.getFirstName(), response.getBody().getFirstName());
	        }
	        
	        @Test
	        @DisplayName("Check (Existing one update Status: 409 CONFLICT)"
	        		+ " - Given a registered MedicalRecord,"
	        		+ " when POST request,"
	        		+ " then MedicalRecord not Added and CONFLICT " +
	                "status is returned")
	        public void testAddRequestForExistingMedicalRecord() {

	            restTemplate.postForEntity(getRootUrl()
	            		+ "/medicalRecord",
	            		medicalRecordToAdd,
	            		MedicalRecordDTO.class);
	            
	            response = restTemplate
	            		.postForEntity(getRootUrl() +
	                    "/medicalRecord",
	                    medicalRecordToAdd,
	                    MedicalRecordDTO.class);

	            assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCodeValue());
	        }

	    
}
