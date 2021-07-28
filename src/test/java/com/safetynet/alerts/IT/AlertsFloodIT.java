package com.safetynet.alerts.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alerts.dto.FloodDTO;

@DisplayName("FLOOD ALERT GET Endpoint Controller - Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlertsFloodIT {

	   @Autowired
	    private TestRestTemplate restTemplate;

	    @LocalServerPort
	    private int port;
		
	    private final static String FLOOD_URL = "/flood/stations?stations={stations}&stations={stations}";
		
	    private String getRootUrl() {
	        return "http://localhost:" + port;
	    }
	    
	    ResponseEntity<FloodDTO> response;
	    
	    @ParameterizedTest
	    @CsvSource({
	      "1, 2",
	      "2, 3",
	      "3, 4",
	      "1, 4",
	      "2, 4"
	    })
	    @DisplayName("Check (Valid Input ResponseStatus: 200 OK)"
	    		+ "Given valid stations numbers,"
	    		+ " when Flood request,"
	    		+ " then return 200 OK status")
	    public void testFloodRequestForValidStationsNumbers(int station1, int station2) {
	        response = restTemplate.getForEntity(
	        		getRootUrl() + FLOOD_URL,
	        		FloodDTO.class, station1,
	        		station2);

	        assertFalse((response.getBody().getHouseholdsByStation()).isEmpty());
	        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	    }

	    @ParameterizedTest
	    @CsvSource({
	      "0, 2",
	      "-1, 3",
	      "999, 4",
	      "1, -100",
	      "2, 999"
	    })
	    @DisplayName("Check (Empty Input ResponseStatus: 409 NOT FOUND REQUEST)"
	    		+ "Given invalid stations numbers,"
	    		+ " when Flood request,"
	    		+ " then return Reponse Status: 409 NOT FOUND REQUEST")
	    public void testFloodRequestForInvalidStationsNumbers(int station1, int station2) {
	        response = restTemplate.getForEntity(getRootUrl() + FLOOD_URL,
	        		FloodDTO.class,
	        		station1, station2);

	        assertNull(response.getBody().getHouseholdsByStation());
	        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	    }
}
