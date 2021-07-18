package com.safetynet.alerts.IT;

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
	    
	    MedicalRecordDTO medicalRecordToGet;
	    
	    ResponseEntity<MedicalRecordDTO> response;
	    
	    @BeforeEach
	  public void setUp() {
	        medicalRecordToGet = new MedicalRecordDTO(
	        		"Test GET First Name",
	        		"Test GET Last Name",
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
	        
	    }

}
