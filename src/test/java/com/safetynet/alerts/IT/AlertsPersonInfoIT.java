package com.safetynet.alerts.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import com.safetynet.alerts.dto.PersonInfoDTO;

@DisplayName("PERSON INFO ALERT GET Endpoint Controller - Integration Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlertsPersonInfoIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
  
    private final static String PERSON_INFO_URL = "/personInfo?firstName={firstName}&lastName={lastName}";

    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
    ResponseEntity<PersonInfoDTO> response;

    @ParameterizedTest
    @CsvSource({
      "John, Boyd",
      "Foster, Shepard",
      "Reginold, Walker",
      "Kendrik, Stelzer",
      "Eric, Cadigan"
    })
    @DisplayName("Check (Valid Input ResponseStatus: 200 OK)"
    		+ "Given a valid person ID,"
    		+ " when Flood request,"
    		+ " then return 200 OK status")
    public void testPersonInfoRequestForValidPersonId(String firstName, String lastName) {
        response = restTemplate
        		.getForEntity(getRootUrl() +
                PERSON_INFO_URL,
                PersonInfoDTO.class,
                firstName, lastName);

        assertFalse((response.getBody().getPersonsInfo()).isEmpty());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Check (Empty Input ResponseStatus: 400 BAD REQUEST)"
    		+ "Given empty person ID,"
    		+ " when Flood request,"
    		+ " then return 400 BAD REQUEST status")
    public void testPersonInfoRequestForEmptyPersonId() {
        response = restTemplate
        		.getForEntity(getRootUrl() +
                PERSON_INFO_URL,
                PersonInfoDTO.class,
                "", "");

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

}
