package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alerts.dto.CommunityEmailDTO;

@DisplayName("COMMUNITY EMAIL - GET Endpoint Controller - Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlertsCommunityEmailIT {

	   @Autowired
	    private TestRestTemplate restTemplate;

	    @LocalServerPort
	    private int port;
		
	    private String getRootUrl() {
	        return "http://localhost:" + port;
	    }
	    
	    private final static String EMAIL_URL = "/communityEmail?city={city}";

	    ResponseEntity<CommunityEmailDTO> response;
	    
	    @Test
	    @DisplayName("Check (Valid Input ResponseStatus: 200 OK)"
	    		+ " - Given a valid city,"
	    		+ " when Email request,"
	    		+ " then return OK status")
	    public void testCommunityEmailForValidInputOKStatusIsReturned() {
	        response = restTemplate.getForEntity(getRootUrl() +
	                EMAIL_URL, CommunityEmailDTO.class, "Culver");

	        assertNotNull(response.getBody());
	        assertEquals((HttpStatus.OK), response.getStatusCode());
	        assertEquals(200, response.getStatusCodeValue());
	        assertFalse((response.getBody().getEmails()).isEmpty());
	        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	    }


	    @Test
	    @DisplayName("Check (Empty Input ResponseStatus: 400 BAD REQUEST)"
	    		+ " - Given empty city,"
	    		+ " when Email request,"
	    		+ " then return BAD REQUEST status")
	    public void givenEmptyCity_whenCommunityEmailRequest_thenReturnBadRequestStatus() {
	        response = restTemplate.getForEntity(getRootUrl() +
	                EMAIL_URL, CommunityEmailDTO.class, "");

	        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
	        assertNull(response.getBody().getEmails());
	        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	    }
	    
	    @ParameterizedTest(name="#{index} - IT - City={0} Response: 409 NOT FOUND")
	    @ValueSource(strings = {
	    		"ABCD Culver",
	    		"XXXXXXX",
	    		"My City",
	    		"32456467865",
	    		"^^^^^~~~"})
	    @DisplayName("Check - <INVALID PERSON ID - 409>"
	    		+ " - Given an Invalid city,"
	    		+ " when Email request,"
	    		+ " then return Reponse Status: 409 NOT FOUND REQUEST")
	    public void testEmailRequestForInvalidCity(String arg) {
	        response = restTemplate
	        		.getForEntity(getRootUrl() +
	                EMAIL_URL, CommunityEmailDTO.class,
	                arg);

	        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
	        assertNull(response.getBody().getEmails());
	        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	    }

}
