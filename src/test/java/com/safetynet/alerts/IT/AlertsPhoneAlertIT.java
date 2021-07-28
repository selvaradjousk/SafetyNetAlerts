package com.safetynet.alerts.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.junit.jupiter.api.DisplayName;
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

import com.safetynet.alerts.dto.PhoneAlertDTO;

@DisplayName("PHONE ALERT GET Endpoint Controller - Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlertsPhoneAlertIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
	
    private final static String PHONE_ALERT_URL = "/phoneAlert?firestation={firestation}";
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }
	
    @ParameterizedTest(name="#{index} - IT - Station No. ={0} Response: 200 OK")
    @ValueSource(ints = {
    		1,
    		2,
    		3,
    		4})
    @DisplayName("Check (Valid Input ResponseStatus: 200 OK)"
    		+ "Given a valid station number,"
    		+ " when PhoneAlert request,"
    		+ " then return 200 OK status")
    public void testPhoneAlertRequestStationNumber(int arg) {
        ResponseEntity<PhoneAlertDTO> response = restTemplate
        		.getForEntity(getRootUrl() +
                PHONE_ALERT_URL, PhoneAlertDTO.class, arg);

        assertFalse((response.getBody().getPhones()).isEmpty());
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @ParameterizedTest(name="#{index} - IT - Station No. ={0} Response: 409 NOT FOUND")
    @ValueSource(ints = {
    		0,
    		-1,
    		999,
    		-100,
    		999})
    @DisplayName("Check (Empty Input ResponseStatus: 409 NOT FOUND REQUEST)"
    		+ "Given an invalid station number,"
    		+ " when PhoneAlert request,"
    		+ " then return Reponse Status: 409 NOT FOUND REQUEST")
    public void testPhoneAlertRequestInvalidStationNumber(int arg) {
        ResponseEntity<PhoneAlertDTO> response = restTemplate
        		.getForEntity(getRootUrl() +
                PHONE_ALERT_URL, PhoneAlertDTO.class, arg);

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

}
