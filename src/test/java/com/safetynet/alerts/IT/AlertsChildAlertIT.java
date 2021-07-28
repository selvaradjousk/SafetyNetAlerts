package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

import com.safetynet.alerts.dto.ChildAlertDTO;

@DisplayName("ChildAlert GET Endpoint Controller - Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlertsChildAlertIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }
	
    private final static String CHILD_ALERT_URL = "/childAlert?address={address}";
    
    ResponseEntity<ChildAlertDTO> response;
    
    @ParameterizedTest(name="#{index} - IT - Address={0} Response: 200 OK")
    @ValueSource(strings = {
    		"1509 Culver St",
    		"29 15th St",
    		"834 Binoc Ave",
    		"489 Manchester St",
    		"748 Townings Dr"})
    @DisplayName("Check (Valid Input ResponseStatus: 200 OK)"
    		+ " - Given a valid address,"
    		+ " when ChildAlert request,"
    		+ " then return 200 OK status")
    public void testGetChildAlertRequestForValidInputResponseOkStatus(String arg) {
        response = restTemplate
        		.getForEntity(getRootUrl() +
                CHILD_ALERT_URL, ChildAlertDTO.class, arg);

        assertNotNull(response.getBody());
        assertEquals((HttpStatus.OK), response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Check (Empty Input ResponseStatus: 400 BAD REQUEST)"
    		+ " - Given an empty address,"
    		+ " when ChildAlert request,"
    		+ " then return Response Status: 400 BAD REQUEST")
    public void testGetChildAlertRequestForEmptyInputResponseBadRequest() {
    	
        response  = restTemplate
        		.getForEntity(getRootUrl() +
        				CHILD_ALERT_URL, ChildAlertDTO.class, "");

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertNull(response.getBody().getChild());
        assertNull(response.getBody().getHomeMembers());
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
    		+ " when ChildAlert request,"
    		+ " then return Reponse Status: 409 NOT FOUND REQUEST")
    public void testChildAlertRequestForInvalidAddress(String arg) {
    	
        response = restTemplate
        		.getForEntity(getRootUrl() +
                CHILD_ALERT_URL, ChildAlertDTO.class, arg);

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertNull(response.getBody().getChild());
        assertNull(response.getBody().getHomeMembers());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

}
