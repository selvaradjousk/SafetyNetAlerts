package com.safetynet.alerts.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

import com.safetynet.alerts.dto.PersonsByStationDTO;


	@ExtendWith(SpringExtension.class)
	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	class AlertsPersonsByStationIT {

	    @Autowired
	    private TestRestTemplate restTemplate;

	    @LocalServerPort
	    private int port;
		
		private final static String PERSONS_BY_STATION_URL = "/fireStation?stationNumber={stationNumber}";
		
		ResponseEntity<PersonsByStationDTO> response;
		
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
	    		+ " - Given a valid station number,"
	    		+ " when PersonsByStation request,"
	    		+ " then return 200 OK status")
	    public void testPersonsByStationRequestForValidStationNumber(int arg) {
	        response = restTemplate.getForEntity(getRootUrl() +
	                PERSONS_BY_STATION_URL, PersonsByStationDTO.class, arg);

	        assertNotNull(response);
	        assertEquals((response.getBody().getAdultNumber() + response.getBody().getChildNumber()), (response.getBody().getPersonsByStation().size()));
	        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
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
	    		+ " when PersonsByStation request,"
	    		+ " then return Reponse Status: 409 NOT FOUND REQUEST")
	    public void testPersonsByStationRequestForInvalidStationNumber(int arg) {
	        response = restTemplate.getForEntity(getRootUrl() +
	                PERSONS_BY_STATION_URL, PersonsByStationDTO.class, arg);

	        assertNull(response.getBody().getPersonsByStation());
	        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	    }

}
