package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dto.CommunityEmailDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AlertsUrlsService;
import com.safetynet.alerts.service.PersonService;

@DisplayName("Alert Services- Get Emails by City - Endpoint")
@ExtendWith(MockitoExtension.class)
class AlertsUrlsServiceGetEmailByCityTest {

	   @InjectMocks
	    private AlertsUrlsService alertsService;
	   
	    @Mock
	    private PersonService personService;
	
	
	    private static List<Person> personList;
	    private static Person person1;
	    private static Person person2;
	    private static Person person3;


	    @BeforeEach
	    public void setUp() {
	    	
	        person1 = new Person(
	        		"Test First Name1",
	        		"Test Last Name1",
	        		"TestStreetName",
	        		"Test City",
	                11111,
	                "9876543210",
	                "myemai1@email.com");
	        
	        person2 = new Person(
	        		"Test FirstName2",
	        		"Test LastName2",
	        		"TestStreetName",
	        		"Test City",
	                11111,
	                "8765432109",
	                "myemai2@email.com");
	        
	        person3 = new Person(
	        		"Test FirstName3",
	        		"Test LastName3",
	        		"Test another Street Name",
	        		"Test City",
	                11111,
	                "77654321098",
	                "myemai3@email.com");
	        
	        personList = Arrays.asList(person1, person2, person3);

	        when(personService
	        		.getPersonsByCity(anyString()))
	        .thenReturn(personList);
	        
	    }
	    
	// ************** getEmailsByCity Tests ****************
	// check result on getEmailsByCity() is not null
	// check result list of phones same as list expected
	
	// verify number of times execution in case of one or multiple executions of a method
	// if possible for invalid and error check
	
    @Test
    @DisplayName("Valid input Not null"
    		+ " - Given an expected emails list,"
    		+ " when getEmailsByCity,"
    		+ " then result not null")
    public void gtestGetEmailsByCityNotNull() {
        CommunityEmailDTO result = alertsService
        		.getEmailsByCity(anyString());

        assertNotNull(result);
        verify(personService).getPersonsByCity(anyString());
    }
    
    @Test
    @DisplayName("Valid input - Result as expected"
    		+ " - Given an expected emails list,"
    		+ " when getEmailsByCity,"
    		+ " then result as expected emails list")
    public void gtestGetEmailsByCityAsExpectedList() {
        List<String> expectedEmails = 
        		Arrays.asList(
        				"myemai1@email.com",
        				"myemai2@email.com",
        				"myemai3@email.com");

        CommunityEmailDTO result = alertsService
        		.getEmailsByCity(anyString());

        assertEquals(expectedEmails, result.getEmails());
    }
}
