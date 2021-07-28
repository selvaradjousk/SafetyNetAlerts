package com.safetynet.alerts.IT;

import static org.junit.jupiter.api.Assertions.*;

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

import com.safetynet.alerts.dto.FireDTO;

@DisplayName("FIRE GET PERSONS BY ADDRESS Endpoint Controller - Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlertsFireIT {

	   @Autowired
	    private TestRestTemplate restTemplate;

	    @LocalServerPort
	    private int port;
		
		private final static String FIRE_URL = "/fire?address={address}";
		
	    private String getRootUrl() {
	        return "http://localhost:" + port;
	    }
		
	    ResponseEntity<FireDTO> response;
	    
	    @ParameterizedTest(name="#{index} - IT - Address={0} Response: 200 OK")
	    @ValueSource(strings = {
	    		"1509 Culver St",
	    		"29 15th St",
	    		"834 Binoc Ave",
	    		"489 Manchester St",
	    		"748 Townings Dr"})
	    @DisplayName("Check (Valid Input ResponseStatus: 200 OK)"
	    		+ "Given a valid address,"
	    		+ " when Fire request,"
	    		+ " then return 200 OK status")
	    public void testFireAlertRequestForAValidAddress(String arg) {
	       response = restTemplate
	    		   .getForEntity(getRootUrl() +
	                FIRE_URL, FireDTO.class,
	                arg);

	        assertFalse((response.getBody().getPersons()).isEmpty());
	        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	    }

	    @Test
	    @DisplayName("Check (Empty Input ResponseStatus: 400 BAD REQUEST)"
	    		+ "Given an empty address,"
	    		+ " when Fire request,"
	    		+ " then return Response Status: 400 BAD REQUEST")
	    public void testFireAlertRequestForEmptyAddress() {
	        response = restTemplate.getForEntity(getRootUrl() + FIRE_URL, FireDTO.class,
	                "");

	        assertNull(response.getBody().getPersons());
	        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	    }

	    @ParameterizedTest(name="#{index} - IT - Address={0} Response: 409 NOT FOUND")
	    @ValueSource(strings = {
	    		"ABCD Culver St",
	    		"XXXXXXX",
	    		"My Address",
	    		"32456467865",
	    		"^^^^^~~~"})
	    @DisplayName("Check - <INVALID Address - 409>"
	    		+ " - Given an invalid address,"
	    		+ " when Fire request,"
	    		+ " then return Reponse Status: 409 NOT FOUND REQUEST")
	    public void testFireAlertRequestForInvalidAddress(String arg) {
	        response = restTemplate
	        		.getForEntity(getRootUrl() + FIRE_URL,
	        				FireDTO.class,
	        				arg);

	        assertNull(response.getBody().getPersons());
	        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	    }
}
