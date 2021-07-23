package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.junit.jupiter.api.AfterEach;
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
import com.safetynet.alerts.dto.PersonDTO;

@DisplayName("PERSON DELETE Endpoints Controller - Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerDeletePersonIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private ObjectMapper objectMapper;
    
    
    PersonDTO testPersonToBeDeleted, testPersonToBeDeletedCopy;
    ResponseEntity<PersonDTO> response, responseDelete;
    
    private final static String PERSON_ID_URL = "/person?firstName={firstName}&lastName={lastName}";
	
	
    @BeforeEach
    public void init() {
    	
        testPersonToBeDeleted = new PersonDTO().builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.address("Testing Street")
        		.city("TestCity")
        		.zip(98765)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();  
        testPersonToBeDeletedCopy = new PersonDTO().builder()
        		.firstName("Test Delete FirstName")
        		.lastName("Test Delete Last Name")
        		.address("Testing Street")
        		.city("TestCity")
        		.zip(98765)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build(); 
    	
  	   restTemplate
	   		.postForEntity(
	   				getRootUrl() + "/person",
	   			testPersonToBeDeleted,
	   				PersonDTO.class);
  	   
        response = restTemplate
     		   .getForEntity(getRootUrl() + PERSON_ID_URL,
                PersonDTO.class,
                testPersonToBeDeleted.getFirstName(),
                testPersonToBeDeleted.getLastName());
    }
    
    @AfterEach
    public void finish() {
             
        restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToBeDeleted.getFirstName(), testPersonToBeDeleted.getLastName()); 
    }
    



@Test
@DisplayName("Check - <RESPONSE GET after Delete 404 NOT FOUND>"
		+ " - Given a Person,"
		+ " when DELETE request,"
		+ " then response on get is 404 NOT FOUND")
public void testDeletePersonRequestWithValidPersonResponseNull() {

	// Confirms person id found before delete
	assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	
	// delete request executed
	restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToBeDeleted.getFirstName(), testPersonToBeDeleted.getLastName());
	
    // Checking that existing person has been deleted
    response = restTemplate.getForEntity(getRootUrl() + PERSON_ID_URL,
            PersonDTO.class, testPersonToBeDeleted.getFirstName(), testPersonToBeDeleted.getLastName());
    
    // response is not null - has information
    assertNotNull(response);
    assertNotNull(response.getHeaders());
    assertNotNull(response.getBody());
    
    // person is not found - confirms delete process
    assertEquals(404, response.getStatusCodeValue());
    assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
}

@Test
@DisplayName("Check - <PERSON GET after Delete Response NOT SAME to Person Data>"
		+ " - Given a Person,"
		+ " when DELETE request,"
		+ " then response on get not same as person data to be deleted")
public void testDeletePersonRequestResponseToGetPersonNotSameAsOriginalPersonData() {

	// Confirms person id found before delete
	assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	
	// delete request executed
	restTemplate.delete(getRootUrl() + PERSON_ID_URL, testPersonToBeDeleted.getFirstName(), testPersonToBeDeleted.getLastName());
	
    // Checking that existing person has been deleted
    response = restTemplate.getForEntity(getRootUrl() + PERSON_ID_URL,
            PersonDTO.class, testPersonToBeDeleted.getFirstName(), testPersonToBeDeleted.getLastName());
    
 // response is not null - has information
    assertNotNull(response);
    assertNotNull(response.getHeaders());
    assertNotNull(response.getBody());
    
 // person to be deleted is not found anymore - confirms delete
    assertThat(response.getBody()).isNotSameAs(testPersonToBeDeleted);
}


@Test
@DisplayName("Check - <PERSON ID param>"
		+ "Given incomplete Person Id,"
		+ " when DELETE request,"
		+ " then person is not deleted")
public void testDeleteRequestInvalidPersonId() {

	responseDelete = restTemplate
 				.postForEntity(
 				getRootUrl() + "/person",
 			testPersonToBeDeletedCopy,
 				PersonDTO.class);
	   
	// Confirms person id found before delete
	assertEquals(HttpStatus.CREATED.value(), responseDelete.getStatusCodeValue());
	
	// delete request with missing and wrong input
    restTemplate.delete(getRootUrl() + PERSON_ID_URL, "firstNameDel", "");

    // Checking that existing person has not been deleted
    response = restTemplate
    		.getForEntity(getRootUrl() + PERSON_ID_URL,
            PersonDTO.class,
            testPersonToBeDeletedCopy.getFirstName(),
            testPersonToBeDeletedCopy.getLastName());

    // get request confirms that it is able to get the original person
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    
    // person data is same as the original - confirms invalid ID delete did not happen
    assertThat(response.getBody())
            .isNotNull()
            .usingRecursiveComparison().isEqualTo(testPersonToBeDeletedCopy);
}
}


