package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AlertsUrlsService;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.ComputeAgeOfPerson;

@DisplayName("Alert Services - Get Child By Address - Endpoint")
@ExtendWith(MockitoExtension.class)
class AlertsUrlsServiceGetChildByAddressTest {

	

	   @InjectMocks
	    private AlertsUrlsService alertsService;

	    @Mock
	    private FireStationService fireStationService;

	    @Mock
	    private MedicalRecordService medicalRecordService;

	    @Mock
	    private PersonService personService;

	    @Mock
	    private ComputeAgeOfPerson computeAgeOfPerson;

	    private static List<Person> personList, expectedPersonsList;
	    private static Person person1;
	    private static Person person2;
	    private static Person person3;

	    private static MedicalRecordDTO medicalRecord1;
	    private static MedicalRecordDTO medicalRecord2;
	    private static MedicalRecordDTO medicalRecord3;

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

	        medicalRecord1 = new MedicalRecordDTO(
	        		"Test First Name1",
	        		"Test Last Name1",
	        		"01/01/1970",
	                Arrays.asList("medication1"),
	                Arrays.asList("allergyyy"));
	        
	        medicalRecord2 = new MedicalRecordDTO(
	        		"Test FirstName2",
	        		"Test LastName2",
	        		"01/01/2010",
	                Arrays.asList("medication3"),
	                Arrays.asList(""));
	        
	        medicalRecord3 = new MedicalRecordDTO(
	        		"Test FirstName3",
	        		"Test LastName3",
	        		"01/01/2000",
	                Arrays.asList(""),
	                Arrays.asList("my allergy"));
	        
	    	
	        when(personService
	        		.getPersonsByAddress(anyString()))
	        .thenReturn(Arrays.asList(person1,
	                person2));
	        
	        when(medicalRecordService
	        		.getMedicalRecordById("Test First Name1", "Test Last Name1"))
	        .thenReturn(medicalRecord1);
	        
	        when(medicalRecordService
	        		.getMedicalRecordById("Test FirstName2", "Test LastName2"))
	        .thenReturn(medicalRecord2);
	        
	        when(computeAgeOfPerson
	        		.getAge(LocalDate.of(1970, 1, 1)))
	        .thenReturn(50);
	        
	        when(computeAgeOfPerson
	        		.getAge(LocalDate.of(2010, 1, 1)))
	        .thenReturn(10);

	    }
	    
	    // ***********************************************************************************
	    
	    @Test
	    @DisplayName("Result NOT NULL"
	    		+ " - Given a address,"
	    		+ " when getChildByAddress,"
	    		+ " then return result not null")
	    public void testGetChildByAddressForValidInputResultNotNull() {
	    	
	    	ChildAlertDTO result = alertsService
	    			.getChildByAddress("TestStreetName");
	    	// check result on getChildByAddress() is not null
	        assertNotNull(alertsService.getChildByAddress("TestStreetName"));
	        assertNotNull(result.getChild());
	        assertNotNull(result.getHomeMembers());
	        assertNotNull(result);	
	    	
	    }
	    
	    
	    @Test
	    @DisplayName("Check Order of Execution"
	    		+ " - Given a address,"
	    		+ " when getChildByAddress,"
	    		+ " then check order of execution ")
	    public void testGetChildByAddressForValidInputCheckOrderOfExecution() {
	    	
	    	alertsService.getChildByAddress("TestStreetName");
	    	// check for the order of execution
	        InOrder inOrder = inOrder(personService, medicalRecordService, computeAgeOfPerson);
	        inOrder.verify(personService).getPersonsByAddress(anyString());
	        inOrder.verify(medicalRecordService).getMedicalRecordById(anyString(), anyString());
	        inOrder.verify(computeAgeOfPerson).getAge(any(LocalDate.class));
	        
	     // verify number of times execution in case of one or multiple executions of a method
	        verify(personService).getPersonsByAddress(anyString());
	        verify(medicalRecordService, times(2)).getMedicalRecordById(anyString(), anyString());
	        verify(computeAgeOfPerson, times(2)).getAge(any(LocalDate.class));
	    }
	    
	    @Test
	    @DisplayName("Result firstname as expected"
	    		+ " - Given a address,"
	    		+ " when getChildByAddress,"
	    		+ " then return result first name as expected")
	    public void testGetChildByAddressForValidInputResultFirstNameAsExpected() {
	    	ChildAlertDTO result = alertsService.getChildByAddress("TestStreetName");
			// check result list has firstname
	        assertNotEquals("Test First Name1", result.getChild().get(0).getFirstName());
	        assertEquals("Test FirstName2", result.getChild().get(0).getFirstName());
	    }
	    
	    @Test
	    @DisplayName("Result lastname as expected"
	    		+ " - Given a address,"
	    		+ " when getChildByAddress,"
	    		+ " then return result lastname as expected")
	    public void testGetChildByAddressForValidInputResultLastNameAsExpected() {
	    	ChildAlertDTO result = alertsService.getChildByAddress("TestStreetName");
			// check result list has lastname
	        assertNotEquals("Test Last Name1", result.getChild().get(0).getLastName());
	        assertEquals("Test LastName2", result.getChild().get(0).getLastName());
	    }
	    

	    @Test
	    @DisplayName("Result Age as expected"
	    		+ " - Given a address,"
	    		+ " when getChildByAddress,"
	    		+ " then return result age as expected")
	    public void testGetChildByAddressForValidInputResultAgeAsExpected() {
	    	alertsService.getChildByAddress("TestStreetName");
		        // check if child age match
		        assertEquals(50, computeAgeOfPerson.getAge(LocalDate.of(1970, 1, 1)));
		        assertEquals(10, computeAgeOfPerson.getAge(LocalDate.of(2010, 1, 1)));
	    }
	    
	    @Test
	    @DisplayName(" Result child and homemember number as expected"
	    		+ " - Given a address,"
	    		+ " when getChildByAddress,"
	    		+ " then return result persons number as expected")
	    public void testGetChildByAddressForValidInputResultPersonsNumberAsExpected() {
	    	ChildAlertDTO result = alertsService.getChildByAddress("TestStreetName");
				// check for number of child and home member for a given address
	         assertEquals(1, result.getChild().size());
	         assertEquals(1, result.getHomeMembers().size());
	    }
}
