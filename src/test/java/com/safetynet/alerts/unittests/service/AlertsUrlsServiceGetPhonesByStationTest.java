package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AlertsUrlsService;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.ComputeAgeOfPerson;

@DisplayName("Alert Services- Get Phones by Station - Endpoint")
@ExtendWith(MockitoExtension.class)
class AlertsUrlsServiceGetPhonesByStationTest {
	
	 @InjectMocks
	    private AlertsUrlsService alertsService;

	    @Mock
	    private FireStationService fireStationService;

	    @Mock
	    private PersonService personService;

	    @Mock
	    private ComputeAgeOfPerson computeAgeOfPerson;


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
	        		.getAllPersonList())
	        .thenReturn(personList);
	        
	        when(fireStationService
	        		.getAddressesByStation(2))
	        .thenReturn(Arrays.asList("TestStreetName", "Test another Street Name"));

	    }
	    

		// ************** getPhoneByStation Tests ****************
		// check result on getPhoneByStation() is not null
		// check result list of phones same as list expected
		
		// check for the order of execution
		// verify number of times execution in case of one or multiple executions of a method
		// if possible for invalid and error check
	    
	    @Test
	    @DisplayName("Result NOT NULL"
	    		+ " - Given an expected phones list,"
	    		+ " when getPhonesByStation,"
	    		+ " then return result not null")
	    public void testGetPhonesByStationForValidInputResultNotNull() {
	    	
	    	PhoneAlertDTO result = alertsService
	    			.getPhonesByStation(2);
	    	
	    	
	    	// check result on getChildByAddress() is not null
	        assertNotNull(fireStationService.getAddressesByStation(2));
	        assertNotNull(result.getPhones());
	        assertNotNull(result);	
	    }

	    @Test
	    @DisplayName("Check Order of Execution"
	    		+ " - Given an expected phones list,"
	    		+ " when getPhonesByStation,"
	    		+ " then check order of execution ")
	    public void testGetPhonesByStationForValidInputCheckOrderOfExecution() {
	    	
	    	alertsService
	    			.getPhonesByStation(2);
	    	
	    	
	    	// check for the order of execution
	        InOrder inOrder = inOrder(personService, fireStationService);
	        inOrder.verify(personService).getAllPersonList();
	        inOrder.verify(fireStationService).getAddressesByStation(anyInt());
	    	
	     // verify number of times execution in case of one or multiple executions of a method
	        verify(personService, times(1)).getAllPersonList();
	        verify(fireStationService, times(1)).getAddressesByStation(anyInt());
	    }

	    @Test
	    @DisplayName("Result on Number of phones as EXPECTED"
	    		+ " - Given an expected phones list,"
	    		+ " when getPhonesByStation,"
	    		+ " then return result number of phones in the list as expected")
	    public void testGetPhonesByStationForValidInputResultNumberOfPhonesInListAsExpected() {
	    	PhoneAlertDTO result = alertsService
	    			.getPhonesByStation(2);

	    	// check result list of phones same as list expected
	    	assertEquals(3, result.getPhones().size());
	    }
	    
	    @Test
	    @DisplayName("Result AS EXPECTED"
	    		+ " - Given an expected phones list,"
	    		+ " when getPhonesByStation,"
	    		+ " then return result list of phones same as list expected")
	    public void testGetPhonesByStationForValidInputResultPhoneListAsExpected() {
	    	List<String> expectedPhones = Arrays.asList("9876543210", "8765432109", "77654321098");
	    	PhoneAlertDTO result = alertsService
	    			.getPhonesByStation(2);

	    	// check result list of phones same as list expected
	    	assertEquals(expectedPhones, result.getPhones());
	    	assertEquals(3, result.getPhones().size());
	    }
}
