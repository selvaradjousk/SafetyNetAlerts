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
	    medicalRecordToAdd, medicalRecordToAddIdNoFirstName,
	    medicalRecordToAddIdNoLastName, medicalRecordToAddIdNoInput,
	    medicalRecordToAddIdNull, medicalRecordToUpdate,
	    medicalRecordUpdated, medicalRecordUpdatedNoFirstName,
	    medicalRecordUpdatedNoLastName, medicalRecordUpdatedNoFirstNameAndLastName,
	    medicalRecordUpdatedNoInvalidIDInput, medicalRecordToDelete;
	    
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
	         
	         medicalRecordToUpdate = new MedicalRecordDTO("firstNamePut",
	         		"lastNamePut",
	         		"01/01/1970",
	         		Arrays.asList("Test Medication1"),
	         		Arrays.asList("Test Allergy1"));

	         medicalRecordUpdated = new MedicalRecordDTO(
	         		"firstNamePut",
	         		"lastNamePut",
	         		"01/01/1970",
	         		Arrays.asList("Test Medication1"),
	         		Arrays.asList("Test Allergy1", "Test Allergy2"));
	         
	         medicalRecordUpdatedNoFirstName = new MedicalRecordDTO(
	         		"",
	         		"lastNamePut",
	         		"01/01/1970",
	         		Arrays.asList("Test Medication1"),
	         		Arrays.asList("Test Allergy1", "Test Allergy2"));
	         
	         medicalRecordUpdatedNoLastName = new MedicalRecordDTO(
	         		"firstNamePut",
	         		"",
	         		"01/01/1970",
	         		Arrays.asList("Test Medication1"),
	         		Arrays.asList("Test Allergy1", "Test Allergy2"));
	         
	         medicalRecordUpdatedNoFirstNameAndLastName = new MedicalRecordDTO(
	         		"",
	         		"",
	         		"01/01/1970",
	         		Arrays.asList("Test Medication1"),
	         		Arrays.asList("Test Allergy1", "Test Allergy2"));
	         
	         medicalRecordUpdatedNoInvalidIDInput = new MedicalRecordDTO(
	         		"XXXXXXX",
	         		"lastNamePut",
	         		"01/01/1970",
	                 Arrays.asList("Test Medication1"),
	                 Arrays.asList("Test Allergy1", "Test Allergy2"));
	         
	         medicalRecordToDelete = new MedicalRecordDTO(
	         		"firstNameDel",
	         		"lastNameDel",
	                 "01/01/1970",
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
	    
	    
	    //*************************************************************************************************
	    
	    
	    @DisplayName("IT - UPDATE MEDICAL RECORD")
	    @Nested
	    class UpdateMedicalRecordIT {  
	    	
	        @BeforeEach
	        public void init() {
	        	restTemplate.postForEntity(
	        			getRootUrl()
	        			+ "/medicalRecord",
	        			medicalRecordToUpdate,
	        			MedicalRecordDTO.class);
	        }
	        
	        
	    @AfterEach
	    public void finish() {
	        restTemplate.delete(getRootUrl()
	        		+ MEDICALRECORD_ID_URL,
	        		medicalRecordToUpdate.getFirstName(),
	        		medicalRecordToUpdate.getLastName());
	    }
	    
	    
	    @Test
	    @DisplayName("Check (Update for Valid Input)"
	    		+ " - Given a MedicalRecord to update,"
	    		+ " when PUT request,"
	    		+ " then MedicalRecord updated")
	    public void testMedicalRecordToUpdateValidInput() {
	        
	        restTemplate.put(getRootUrl()
	        		+ "/medicalRecord",
	        		medicalRecordUpdated);

	     // Verify modification due update
	        response = restTemplate
	        		.getForEntity(getRootUrl() +
	                MEDICALRECORD_ID_URL,
	                MedicalRecordDTO.class,
	                medicalRecordToUpdate.getFirstName(),
	                medicalRecordToUpdate.getLastName());

	        assertNotNull(response.getBody());
	        assertThat(response.getBody())
	                .usingRecursiveComparison().isEqualTo(medicalRecordUpdated);
	    }
	    
	    @Test
	    @DisplayName("Check (Update for No Firstname Id Valid Input)"
	    		+ " - Given a MedicalRecord to update with no firstname input,"
	    		+ " when PUT request,"
	    		+ " then MedicalRecord not updated")
	      public void testMedicalRecordUpdateWithNoFirstNameInput() {

	       restTemplate.put(getRootUrl()
	    		   + "/medicalRecord",
	    		   medicalRecordUpdatedNoFirstName);

	       // Verify no modification is done due update
	        response = restTemplate
	        		.getForEntity(getRootUrl() +
	                MEDICALRECORD_ID_URL,
	                MedicalRecordDTO.class,
	                medicalRecordToUpdate.getFirstName(),
	                medicalRecordToUpdate.getLastName());

	        assertNotNull(response.getBody());
	        assertFalse((response.getBody().getAllergies()).toString().contains("Test Allergy2"));
	    }
	    
	    @Test
	    @DisplayName("Check (Update for NoLastname Id Valid Input)"
	    		+ " - Given a MedicalRecord to update with no Lastname input,"
	    		+ " when PUT request,"
	    		+ " then MedicalRecord not updated")
	      public void testMedicalRecordUpdateWithNoLastNameInput() {

	       restTemplate.put(getRootUrl()
	    		   + "/medicalRecord",
	    		   medicalRecordUpdatedNoLastName);

	       // Verify no modification is done due update
	        response = restTemplate
	        		.getForEntity(getRootUrl() +
	                MEDICALRECORD_ID_URL,
	                MedicalRecordDTO.class,
	                medicalRecordToUpdate.getFirstName(),
	                medicalRecordToUpdate.getLastName());

	        assertNotNull(response.getBody());
	        assertFalse((response.getBody().getAllergies()).toString().contains("Test Allergy2"));
	    }
	    
	    @Test
	    @DisplayName("Check (Update for No Id Valid Input)"
	    		+ " - Given a MedicalRecord to update with no ID input,"
	    		+ " when PUT request,"
	    		+ " then MedicalRecord not updated")
	      public void testMedicalRecordUpdateWithNoFirstNameAndLastNameInput() {

	       restTemplate.put(getRootUrl()
	    		   + "/medicalRecord",
	    		   medicalRecordUpdatedNoFirstNameAndLastName);

	       // Verify no modification is done due update
	        response = restTemplate
	        		.getForEntity(getRootUrl() +
	                MEDICALRECORD_ID_URL,
	                MedicalRecordDTO.class,
	                medicalRecordToUpdate.getFirstName(),
	                medicalRecordToUpdate.getLastName());

	        assertNotNull(response.getBody());
	        assertFalse((response.getBody().getAllergies()).toString().contains("Test Allergy2"));
	    }
	    
	    @Test
	    @DisplayName("Check (Update for No Id Valid Input)"
	    		+ " - Given a MedicalRecord to update with no ID input,"
	    		+ " when PUT request,"
	    		+ " then MedicalRecord not updated")
	      public void testMedicalRecordUpdateWithNoValidIdInput() {

	       restTemplate.put(getRootUrl()
	    		   + "/medicalRecord",
	    		   medicalRecordUpdatedNoInvalidIDInput);

	       // Verify no modification is done due update
	        response = restTemplate
	        		.getForEntity(getRootUrl() +
	                MEDICALRECORD_ID_URL,
	                MedicalRecordDTO.class,
	                medicalRecordToUpdate.getFirstName(),
	                medicalRecordToUpdate.getLastName());

	        assertNotNull(response.getBody());
	        assertFalse((response.getBody().getAllergies()).toString().contains("Test Allergy2"));
	    }
	    }

	    //*************************************************************************************************
	    
	    
	    @DisplayName("IT - DELETE MEDICAL RECORD")
	    @Nested
	    class DeleteMedicalRecordIT {  
	    	
	        @BeforeEach
	        public void init() {
	        	restTemplate.postForEntity(getRootUrl()
	        			+ "/medicalRecord",
	        			medicalRecordToDelete,
	        			MedicalRecordDTO.class);
	        }
	    
	        
	    @Test
	    @DisplayName("Check (VALID Input)"
	    		+ " - Given ID param VALID,"
	    		+ " when DELETE request,"
	    		+ " then MedicalRecord is deleted")
	    public void gtestDeleteRequestValidInput() {

	        restTemplate.delete(getRootUrl()
	        		+ MEDICALRECORD_ID_URL,
	        		medicalRecordToDelete.getFirstName(),
	        		medicalRecordToDelete.getLastName());

	        // Verify MedicalRecord deletion done
	        response = restTemplate
	        		.getForEntity(getRootUrl() +
	                MEDICALRECORD_ID_URL,
	                MedicalRecordDTO.class,
	                medicalRecordToDelete.getFirstName(),
	                medicalRecordToDelete.getLastName());

	        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
	        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());

	    }
	    

	    @Test
	    @DisplayName("Check (for missing firstName)"
	    		+ " - Given ID param without firstname,"
	    		+ " when DELETE request,"
	    		+ " then MedicalRecord is not deleted")
	    public void testDeleteRequestIdNoFirstName() {

	    	// Delete request with ID without firstName
	        restTemplate.delete(getRootUrl()
	        		+ MEDICALRECORD_ID_URL, "firstNameDel", "");

	     // Verify medical record exists not deleted
	        response = restTemplate.getForEntity(getRootUrl() +
	                MEDICALRECORD_ID_URL,
	                MedicalRecordDTO.class,
	                medicalRecordToDelete.getFirstName(),
	                medicalRecordToDelete.getLastName());

	        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	        assertNotNull(response.getBody());
	        assertThat(response.getBody())
	                .usingRecursiveComparison().isEqualTo(medicalRecordToDelete);
	    }
	    
	    @Test
	    @DisplayName("Check (for missing lastName)"
	    		+ " - Given ID param without lastname,"
	    		+ " when DELETE request,"
	    		+ " then MedicalRecord is not deleted")
	    public void testDeleteRequestIdNoLastName() {

	    	// Delete request with ID without lastName
	        restTemplate.delete(getRootUrl()
	        		+ MEDICALRECORD_ID_URL, "", "lastNameDel");

	     // Verify medical record exists not deleted
	        response = restTemplate
	        		.getForEntity(getRootUrl() +
	                MEDICALRECORD_ID_URL,
	                MedicalRecordDTO.class,
	                medicalRecordToDelete.getFirstName(),
	                medicalRecordToDelete.getLastName());

	        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	        assertNotNull(response.getBody());
	        assertThat(response.getBody())
	                .usingRecursiveComparison().isEqualTo(medicalRecordToDelete);
	    }
	    
	    
	    @Test
	    @DisplayName("Check (for No Input Parameters)"
	    		+ " - Given ID param no input,"
	    		+ " when DELETE request,"
	    		+ " then MedicalRecord is not deleted")
	    public void testDeleteRequestNoIdParam() {

	    	// Delete request with no input parameters
	        restTemplate.delete(getRootUrl()
	        		+ MEDICALRECORD_ID_URL, "", "");

	        // Verify medical record exists not deleted
	        response = restTemplate
	        		.getForEntity(getRootUrl() +
	                MEDICALRECORD_ID_URL,
	                MedicalRecordDTO.class,
	                medicalRecordToDelete.getFirstName(),
	                medicalRecordToDelete.getLastName());

	        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	        assertNotNull(response.getBody());
	        assertThat(response.getBody())
	                .usingRecursiveComparison().isEqualTo(medicalRecordToDelete);
	    }
	    
	    
	    
	    }
	    
}
