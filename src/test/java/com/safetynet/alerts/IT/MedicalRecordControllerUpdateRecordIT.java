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

@DisplayName("MedicalRecord UPDATE Controller - Integration Tests")
	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class MedicalRecordControllerUpdateRecordIT {

	    @Autowired
	    private TestRestTemplate restTemplate;

	    @LocalServerPort
	    private int port;

	    private final static String MEDICALRECORD_ID_URL = "/medicalRecord?firstName={firstName}&lastName={lastName}";

	    private String getRootUrl() {
	        return "http://localhost:" + port;
	    }

	    private ObjectMapper objectMapper;
	    
	    MedicalRecordDTO medicalRecordToUpdate,
	    medicalRecordUpdated, medicalRecordUpdatedNoFirstName,
	    medicalRecordUpdatedNoLastName, medicalRecordUpdatedNoFirstNameAndLastName,
	    medicalRecordUpdatedNoInvalidIDInput;
	    
	    ResponseEntity<MedicalRecordDTO> response;
	    
	    @BeforeEach
	  public void setUp() {

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
