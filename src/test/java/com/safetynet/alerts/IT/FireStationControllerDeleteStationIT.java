package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

@DisplayName("FIRESTATION DELETE ENDPOINT- Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FireStationControllerDeleteStationIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private final static String FIRESTATION_ID_URL = "/firestation?station={station}&address={address}";

    private ObjectMapper objectMapper;
    
    FireStationDTO  fireStationToBeDeleted, fireStationToBeDeletedCopy;
    
    ResponseEntity<FireStationDTO> response, responseOnPost, responseDelete;
    ResponseEntity<FireStationDTO> getFireStationAdded;
    
    @BeforeEach
  public void setUp() {
    	objectMapper = new ObjectMapper();
 
    	
    	fireStationToBeDeleted = new FireStationDTO().builder()
    			.stationId(3)
    			.address("addressAddedtoDelete")
    			.build();
    	
    	
    	fireStationToBeDeletedCopy = new FireStationDTO().builder()
    			.stationId(3)
    			.address("addressAddedtoBeDeleted")
    			.build();
        
   	   restTemplate
	   		.postForEntity(
	   				getRootUrl() + "/firestation",
	   			fireStationToBeDeleted,
	   				FireStationDTO.class);
   	   
         response = restTemplate
      		   .getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                 FireStationDTO.class,
                 fireStationToBeDeleted.getStationId(),
                 fireStationToBeDeleted.getAddress());
    	
     }

        @AfterEach
        public void finish() {
                 
            restTemplate.delete(getRootUrl() + FIRESTATION_ID_URL, fireStationToBeDeleted.getStationId(), fireStationToBeDeleted.getAddress()); 
        }
        
   
    @Test
    @DisplayName("Check - <RESPONSE GET after Delete 404 NOT FOUND>"
    		+ " - Given a FireStation,"
    		+ " when DELETE request,"
    		+ " then response on get is 404 NOT FOUND")
    public void testDeleteFireStationRequestWithValidFireStationResponseNull() {

    	// Confirms firestation  found before delete
    	assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    	
    	// delete request executed
    	restTemplate.delete(getRootUrl() + FIRESTATION_ID_URL, fireStationToBeDeleted.getStationId(), fireStationToBeDeleted.getAddress());
    	
        // Checking that existing fireStation has been deleted
        response = restTemplate.getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                FireStationDTO.class, fireStationToBeDeleted.getStationId(), fireStationToBeDeleted.getAddress());
        
        // response is not null - has information
        assertNotNull(response);
        assertNotNull(response.getHeaders());
        assertNotNull(response.getBody());
        
        // fireStation is not found - confirms delete process
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }
    
    
    @Test
    @DisplayName("Check - <FIRESTATION GET after Delete Response NOT SAME to Person Data>"
    		+ " - Given a FireStation,"
    		+ " when DELETE request,"
    		+ " then response on get not same as fireStation data to be deleted")
    public void testDeleteFireStationRequestResponseToGetFireStationNotSameAsOriginalPersonData() {

    	// Confirms fireStation id found before delete
    	assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    	
    	// delete request executed
    	restTemplate.delete(getRootUrl() + FIRESTATION_ID_URL, fireStationToBeDeleted.getStationId(), fireStationToBeDeleted.getAddress());
    	
        // Checking that existing fireStation has been deleted
        response = restTemplate.getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                FireStationDTO.class, fireStationToBeDeleted.getStationId(), fireStationToBeDeleted.getAddress());
        
     // response is not null - has information
        assertNotNull(response);
        assertNotNull(response.getHeaders());
        assertNotNull(response.getBody());
        
     // fireStation to be deleted is not found anymore - confirms delete
        assertThat(response.getBody()).isNotSameAs(fireStationToBeDeleted);
   }
    
    
    @Test
    @DisplayName("Check - <FIRESTATION ID param>"
    		+ "Given incomplete FireStation Id,"
    		+ " when DELETE request,"
    		+ " then FireStation is not deleted")
    public void testDeleteRequestInvalidFireStationId() {

    	responseDelete = restTemplate
	   				.postForEntity(
	   				getRootUrl() + "/firestation",
	   			fireStationToBeDeletedCopy,
	   				FireStationDTO.class);
   	   
    	// Confirms fireStation id found before delete
    	assertEquals(HttpStatus.CREATED.value(), responseDelete.getStatusCodeValue());
    	
    	// delete request with missing and wrong input
        restTemplate.delete(getRootUrl() + FIRESTATION_ID_URL, "firstNameDel", "");

        // Checking that existing fireStation has not been deleted
        response = restTemplate
        		.getForEntity(getRootUrl() + FIRESTATION_ID_URL,
                FireStationDTO.class,
                fireStationToBeDeletedCopy.getStationId(),
                fireStationToBeDeletedCopy.getAddress());

        // get request confirms that it is able to get the original fireStation
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        
        // fireStaiton data is same as the original - confirms invalid ID delete did not happen
        assertThat(response.getBody())
                .isNotNull()
                .usingRecursiveComparison().isEqualTo(fireStationToBeDeletedCopy);
    }

   
}
