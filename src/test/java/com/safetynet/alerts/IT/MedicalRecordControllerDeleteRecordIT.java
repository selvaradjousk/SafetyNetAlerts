package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

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
import com.safetynet.alerts.dto.MedicalRecordDTO;

@DisplayName("MedicalRecord DELETE Controller - Integration Tests")
	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class MedicalRecordControllerDeleteRecordIT {

	    @Autowired
	    private TestRestTemplate restTemplate;

	    @LocalServerPort
	    private int port;

	    private final static String MEDICALRECORD_ID_URL = "/medicalRecord?firstName={firstName}&lastName={lastName}";

	    private String getRootUrl() {
	        return "http://localhost:" + port;
	    }

	    private ObjectMapper objectMapper;
	    
	    MedicalRecordDTO medicalRecordToDelete;
	    
	    ResponseEntity<MedicalRecordDTO> response;
	    
	    @BeforeEach
	  public void setUp() {

	         medicalRecordToDelete = new MedicalRecordDTO(
	         		"firstNameDel",
	         		"lastNameDel",
	                 "01/01/1970",
	                 Arrays.asList("Test Medication1"),
	                 Arrays.asList("Test Allergy1"));
	         
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
	    
	    @Test
	    @DisplayName("Check (for Wrong FirstName Input Parameters)"
	    		+ " - Given ID param  Wrong FirstName Input,"
	    		+ " when DELETE request,"
	    		+ " then MedicalRecord is not deleted")
	    public void testDeleteRequestUnknownRecordInput() {

	    	// Delete request with wrong firstname input parameters
	        restTemplate.delete(getRootUrl()
	        		+ MEDICALRECORD_ID_URL, "XXXXXXXXX", "lastNameDel");

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
