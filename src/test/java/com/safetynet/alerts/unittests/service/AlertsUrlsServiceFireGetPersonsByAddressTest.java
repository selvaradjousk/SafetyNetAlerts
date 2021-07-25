package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.*;
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

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AlertsUrlsService;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.ComputeAgeOfPerson;

@DisplayName("Alert Services Fire - Get Persons By Address - Endpoint")
@ExtendWith(MockitoExtension.class)
class AlertsUrlsServiceFireGetPersonsByAddressTest {

	
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

	    private static List<Person> personList;
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
	        		.getPersonsByAddress("TestStreetName"))
	        .thenReturn(Arrays.asList(person1, person2));
	        
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
	        
	        when(fireStationService
	        		.getFireStationByAddress("TestStreetName"))
	        .thenReturn(new FireStation(1, "TestStreetName"));
	    }
	    
	    
	    @Test
	    @DisplayName("Result NOT NULL"
	    		+ " - Given an address,"
	    		+ " when getPersonsByAddress,"
	    		+ " then return result not null")
	    public void testGetPersonsByAddressForValidInputResultNotNull() {
	    	
	    FireDTO result = alertsService.getPersonsByAddress("TestStreetName");	
	    	
	    assertNotNull(result);
	    assertNotNull(result.getPersons());
	    assertNotNull(result.getStation());
	    assertNotNull(fireStationService.getFireStationByAddress("TestStreetName"));
	    
	    }
	    
	    
	    @Test
	    @DisplayName("Check Order of Execution"
	    		+ " - Given an address,"
	    		+ " when getPersonsByAddress,"
	    		+ " then check order of execution ")
	    public void testGetPersonsByAddressForValidInputCheckOrderOfExecution() {
	    	
	    alertsService.getPersonsByAddress("TestStreetName");	

	    
	    // check for the order of execution
	    InOrder inOrder = inOrder(personService, medicalRecordService, computeAgeOfPerson, fireStationService);
	    inOrder.verify(personService).getPersonsByAddress(anyString());
	    inOrder.verify(medicalRecordService).getMedicalRecordById(anyString(), anyString());
	    inOrder.verify(computeAgeOfPerson).getAge(any(LocalDate.class));
	    inOrder.verify(fireStationService).getFireStationByAddress(anyString());
	    
	    // verify number of times execution in case of one or multiple executions of a method
        verify(personService).getPersonsByAddress(anyString());
        verify(medicalRecordService, times(2)).getMedicalRecordById(anyString(), anyString());
        verify(computeAgeOfPerson, times(2)).getAge(any(LocalDate.class));
        verify(fireStationService).getFireStationByAddress(anyString());
	    
	    }
	    
	    @Test
	    @DisplayName("Result Last name as expected"
	    		+ " - Given a station number,"
	    		+ " when getPersonsByAddress,"
	    		+ " then return result Last name as expected")
	    public void testGetPersonsByAddressForValidInputResultLastNameAsExpected() {
	    	
	    FireDTO result = alertsService.getPersonsByAddress("TestStreetName");	

		// check result list of persons living same as list expected
	    // check result list has lastname
        assertEquals("Test Last Name1", result.getPersons().get(0).getLastName());
        assertEquals("Test LastName2", result.getPersons().get(1).getLastName());
	    }
	    
	    @Test
	    @DisplayName("Result telephone no. as expected"
	    		+ " - Given a station number,"
	    		+ " when getPersonsByAddress,"
	    		+ " then return result telephone no. as expected")
	    public void testGetPersonsByAddressForValidInputResultPhoneAsExpected() {
	    	
	    FireDTO result = alertsService.getPersonsByAddress("TestStreetName");	

		// check result list of persons living same as list expected
	    // check result list has phones
        assertEquals("9876543210", result.getPersons().get(0).getPhone());
        assertEquals("8765432109", result.getPersons().get(1).getPhone());
	    }
	    
	    
	    @Test
	    @DisplayName("Result Age as expected"
	    		+ " - Given a station number,"
	    		+ " when getPersonsByAddress,"
	    		+ " then return result Age as expected")
	    public void testGetPersonsByAddressForValidInputResulAgeAsExpected() {
	    	
	    FireDTO result = alertsService.getPersonsByAddress("TestStreetName");	

		// check result list of persons living same as list expected
	    // check result list has age
        assertEquals(50, result.getPersons().get(0).getAge());
        assertEquals(10, result.getPersons().get(1).getAge());
	    }
	    
	    @Test
	    @DisplayName("Result Medications as expected"
	    		+ " - Given a station number,"
	    		+ " when getPersonsByAddress,"
	    		+ " then return result Medications as expected")
	    public void testGetPersonsByAddressForValidInputResulMedicationsAsExpected() {
	    	
	    FireDTO result = alertsService.getPersonsByAddress("TestStreetName");	

		// check result list of persons living same as list expected
	    // check result list has medications
        assertEquals(Arrays.asList("medication1"), result.getPersons().get(0).getMedications());
        assertEquals(Arrays.asList("medication3"), result.getPersons().get(1).getMedications());
	    }
	    
	    @Test
	    @DisplayName("Result Allergies as expected"
	    		+ " - Given a station number,"
	    		+ " when getPersonsByAddress,"
	    		+ " then return result Allergies as expected")
	    public void testGetPersonsByAddressForValidInputResulAllergiesAsExpected() {
	    	
	    FireDTO result = alertsService.getPersonsByAddress("TestStreetName");	

		// check result list of persons living same as list expected
	    // check result list has Allergies
        assertEquals(Arrays.asList("allergyyy"), result.getPersons().get(0).getAllergies());
        assertEquals(Arrays.asList(""), result.getPersons().get(1).getAllergies());
	    }
	
}
