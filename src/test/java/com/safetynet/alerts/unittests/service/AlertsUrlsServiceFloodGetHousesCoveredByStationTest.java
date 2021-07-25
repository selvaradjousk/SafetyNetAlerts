package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AlertsUrlsService;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.ComputeAgeOfPerson;

@DisplayName("Alert Services Flood - Get Houses Covered By Station - Endpoint")
@ExtendWith(MockitoExtension.class)
class AlertsUrlsServiceFloodGetHousesCoveredByStationTest {


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
	        
	        when(fireStationService
	        		.getAddressesByStation(2))
	        .thenReturn(Arrays.asList("TestStreetName"));
	        
	        when(fireStationService
	        		.getAddressesByStation(1))
	        .thenReturn(Arrays.asList("Test another Street Name"));
	        
	        when(personService
	        		.getPersonsByAddress("TestStreetName"))
	        .thenReturn(Arrays.asList(person1, person2));
	        
	        when(personService
	        		.getPersonsByAddress("Test another Street Name"))
	        .thenReturn(Arrays.asList(person3));
	        
	        when(medicalRecordService
	        		.getMedicalRecordById("Test First Name1", "Test Last Name1"))
	        .thenReturn(medicalRecord1);
	        
	        when(medicalRecordService
	        		.getMedicalRecordById("Test FirstName2", "Test LastName2"))
	        .thenReturn(medicalRecord2);
	        
	        when(medicalRecordService
	        		.getMedicalRecordById("Test FirstName3", "Test LastName3"))
	        .thenReturn(medicalRecord3);
	        
	        when(computeAgeOfPerson
	        		.getAge(any(LocalDate.class)))
	        .thenReturn(anyInt());

	    }
	    
		// check result on getHousesCoveredByStation() is not null
	    @Test
	    @DisplayName("Result NOT NULL"
	    		+ " - Given a station number,"
	    		+ " when getPersonsByStation,"
	    		+ " then return result not null")
	    public void testGetPersonsByStationForValidInputResultNotNull() {
	    	
	    	FloodDTO result = alertsService.getHousesCoveredByStation(Arrays.asList(2, 1));

	        assertNotNull(result);
	        assertNotNull(result.getHouseholdsByStation());
	        assertNotNull(result.getHouseholdsByStation().get(0).getStation());
	        assertNotNull(result.getHouseholdsByStation().get(0).getHouseholds());
	        assertNotNull(result.getHouseholdsByStation().size());
	    }
	    
	
		// check result on getHousesCoveredByStation() is not null
	    @Test
	    @DisplayName("Check Order of Execution"
	    		+ " - Given a station number,"
	    		+ " when getPersonsByStation,"
	    		+ " then check order of execution ")
	    public void testGetPersonsByStationForValidInputCheckOrderOfExecution() {
	    	
	    	alertsService.getHousesCoveredByStation(Arrays.asList(2, 1));

	    	// check for the order of execution
	    	InOrder inOrder = inOrder(fireStationService, personService, medicalRecordService, computeAgeOfPerson);
	    	inOrder.verify(fireStationService).getAddressesByStation(anyInt());
	    	inOrder.verify(personService).getPersonsByAddress(anyString());
	    	inOrder.verify(medicalRecordService).getMedicalRecordById(anyString(), anyString());
	    	inOrder.verify(computeAgeOfPerson).getAge(any(LocalDate.class));
	    	
	    	// verify number of times execution in case of one or multiple executions of a method
	        verify(fireStationService, times(2)).getAddressesByStation(anyInt());
	        verify(personService, times(2)).getPersonsByAddress(anyString());
	        verify(medicalRecordService, times(3)).getMedicalRecordById(anyString(), anyString());
	        verify(computeAgeOfPerson, times(3)).getAge(any(LocalDate.class));
	    }
	    
	    @Test
	    @DisplayName("Result NOT NULL"
	    		+ " - Given a station number,"
	    		+ " when getPersonsByStation,"
	    		+ " then return result not null")
	    public void testGetPersonsByStationForValidInputResultNotNullxx() {

	    	FloodDTO result = alertsService.getHousesCoveredByStation(Arrays.asList(2, 1));

	        assertNotNull(result);
	        assertEquals(2, result.getHouseholdsByStation().size());
	        assertEquals(2, result.getHouseholdsByStation().get(0).getStation());
	        assertEquals(1, result.getHouseholdsByStation().get(1).getStation());
	        assertEquals(1, result.getHouseholdsByStation().get(0).getHouseholds().size());
	        assertEquals(1, result.getHouseholdsByStation().get(1).getHouseholds().size());

	    }
    



}
