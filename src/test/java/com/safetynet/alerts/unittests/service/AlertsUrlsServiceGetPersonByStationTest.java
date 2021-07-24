package com.safetynet.alerts.unittests.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AlertsUrlsService;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.ComputeAgeOfPerson;

@DisplayName("Alert Services Get Persons By Station - Endpoint")
@ExtendWith(MockitoExtension.class)
class AlertsUrlsServiceGetPersonByStationTest {

	
	// ************** getPersonsByStation Tests ****************
	// check result on getPersonsByStation() is not null
	// check result list of persons living same as list expected
	// check result list has firstname, lastname, address  and telephone number
	// check if persons list for a given station number is retrieved correctly
	// check if persons age match
	// check for number of persons for a given station number
	// check for number of adult persons for a given station number
	// check for number of child or infant persons for a given station number
	
	// check for the order of execution
	// verify number of times execution in case of one or multiple executions of a method
	// if possible for invalid and error check

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
	    }

	    @Test
	    @DisplayName("Given a station number,"
	    		+ " when getPersonsByStation,"
	    		+ " then return result not null")
	    public void testGetPersonsByStationForValidInputResultNotNull() {
	    	
	        when(personService
	        		.getAllPersonList())
	        .thenReturn(personList);
	        
	        when(fireStationService
	        		.getAddressesByStation(2))
	        .thenReturn(Arrays.asList("TestStreetName"));
	        
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

	        PersonsByStationDTO result = alertsService.getPersonsByStation(2);

	        assertNotNull(result.getPersonsByStation());
	        assertNotNull((fireStationService.getAddressesByStation(2)));
	        assertNotNull(medicalRecord1);
	        assertNotNull(medicalRecord2);
	        assertNotNull(result);
	    }
	}
