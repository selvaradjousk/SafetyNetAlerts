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

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AlertsUrlsService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.ComputeAgeOfPerson;

@DisplayName("Alert Services- Get Person Info - Endpoint")
@ExtendWith(MockitoExtension.class)
class AlertsUrlsServiceGetPersonInfoTest {

	
	   @InjectMocks
	    private AlertsUrlsService alertsService;

	    @Mock
	    private MedicalRecordService medicalRecordService;

	    @Mock
	    private PersonService personService;

	    @Mock
	    private ComputeAgeOfPerson computeAgeOfPerson;


	    private static Person person1;
	    private static Person person2;
	    private static Person person3;

	    private static List<Person> personList;
	    
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
	        		.getAllPersonList())
	        .thenReturn(personList);
	        
	        when(medicalRecordService
	        		.getMedicalRecordById(anyString(), anyString()))
	        .thenReturn(medicalRecord1);
	        
	        when(computeAgeOfPerson
	        		.getAge(any(LocalDate.class)))
	        .thenReturn(50);
	    }
	    
	    @Test
	    @DisplayName("Result NOT NULL"
	    		+ " - Given a person Id,"
	    		+ " when getInfoByIdentity,"
	    		+ " then return result not null")
	    public void testGetInfoByIdentityForValidInputResultNotNull() {
	    	
	    	PersonInfoDTO result = alertsService
	    			.getInfoPersonByIdentity("Test First Name1", "Test Last Name1");

	    	// check result on getPersonsInfoByIdentity() and its entities are not null
	        assertNotNull(result.getPersonsInfo());
	        assertNotNull(result);
	        
	        // check result list has info on LastName
	        assertNotNull(result.getPersonsInfo().get(0).getLastName());
	        
	        // check result list has info on address
	        assertNotNull(result.getPersonsInfo().get(0).getAddress());
	        
	        // check result list has info on age
	        assertNotNull(result.getPersonsInfo().get(0).getAge());
	        
	        // check result list has info on email
	        assertNotNull(result.getPersonsInfo().get(0).getEmail());
	        
	        // check result list has info on medications
	        assertNotNull(result.getPersonsInfo().get(0).getMedications());
	        
	        // check result list has info on allergies
	        assertNotNull(result.getPersonsInfo().get(0).getAllergies());
	    }
	    
	    
	    
	    @Test
	    @DisplayName("Check Order of Execution"
	    		+ " - Given a person Id,"
	    		+ " when getInfoByIdentity,"
	    		+ " then check order of execution ")
	    public void testGetInfoByIdentityForValidInputExecutionOrder() {
	    	
	    	alertsService
	    			.getInfoPersonByIdentity("Test First Name1", "Test Last Name1");
	    	
	    	// check for the order of execution
	        InOrder inOrder = inOrder(personService, medicalRecordService, computeAgeOfPerson);
	        inOrder.verify(personService).getAllPersonList();
	        inOrder.verify(medicalRecordService).getMedicalRecordById(anyString(), anyString());
	        inOrder.verify(computeAgeOfPerson).getAge(any(LocalDate.class));
	    	
	        // verify number of times execution in case of one or multiple executions of a method
	        verify(personService, times(1)).getAllPersonList();
	        verify(medicalRecordService, times(1)).getMedicalRecordById(anyString(), anyString());
	        verify(computeAgeOfPerson, times(1)).getAge(any(LocalDate.class));
	    }
	    
	    @Test
	    @DisplayName("Result Last Name As Expected"
	    		+ " - Given a person Id,"
	    		+ " when getInfoByIdentity,"
	    		+ " then return result Last Name As Expected")
	    public void testGetInfoByIdentityForValidInputResultLastNameAsExpected() {
	    	
	    	PersonInfoDTO result = alertsService
	    			.getInfoPersonByIdentity("Test First Name1", "Test Last Name1");

	        // check result info on LastName
	        assertEquals(person1.getLastName(), result.getPersonsInfo().get(0).getLastName());
	  
	    }
	    
	    @Test
	    @DisplayName("Result Address As Expected"
	    		+ " - Given a person Id,"
	    		+ " when getInfoByIdentity,"
	    		+ " then return result Address As Expected")
	    public void testGetInfoByIdentityForValidInputResultAddressAsExpected() {
	    	
	    	PersonInfoDTO result = alertsService
	    			.getInfoPersonByIdentity("Test First Name1", "Test Last Name1");

	        // check result info on Address
	        assertEquals(person1.getAddress(), result.getPersonsInfo().get(0).getAddress());
	    }
	    
	    @Test
	    @DisplayName("Result Age As Expected"
	    		+ " - Given a person Id,"
	    		+ " when getInfoByIdentity,"
	    		+ " then return result Address As Expected")
	    public void testGetInfoByIdentityForValidInputResultAgeAsExpected() {
	    	
	    	PersonInfoDTO result = alertsService
	    			.getInfoPersonByIdentity("Test First Name1", "Test Last Name1");

	        // check result info on Age
	        assertEquals(50, result.getPersonsInfo().get(0).getAge());
	    }
	    
	    @Test
	    @DisplayName("Result Email As Expected"
	    		+ " - Given a person Id,"
	    		+ " when getInfoByIdentity,"
	    		+ " then return result Email As Expected")
	    public void testGetInfoByIdentityForValidInputResultEmailAsExpected() {
	    	
	    	PersonInfoDTO result = alertsService
	    			.getInfoPersonByIdentity("Test First Name1", "Test Last Name1");

	        // check result info on Email
	        assertEquals(person1.getEmail(), result.getPersonsInfo().get(0).getEmail());
	    }
	    
	    @Test
	    @DisplayName("Result Medications As Expected"
	    		+ " - Given a person Id,"
	    		+ " when getInfoByIdentity,"
	    		+ " then return result Medications As Expected")
	    public void testGetInfoByIdentityForValidInputResultMedicationsAsExpected() {
	    	
	    	PersonInfoDTO result = alertsService
	    			.getInfoPersonByIdentity("Test First Name1", "Test Last Name1");

	        // check result info on Medications
	        assertEquals(medicalRecord1.getMedications(), result.getPersonsInfo().get(0).getMedications());
	    }
	    
	    @Test
	    @DisplayName("Result Allergies As Expected"
	    		+ " - Given a person Id,"
	    		+ " when getInfoByIdentity,"
	    		+ " then return result Allergies As Expected")
	    public void testGetInfoByIdentityForValidInputResultAllergiesAsExpected() {
	    	
	    	PersonInfoDTO result = alertsService
	    			.getInfoPersonByIdentity("Test First Name1", "Test Last Name1");

	        // check result info on Allergies
	        assertEquals(medicalRecord1.getAllergies(), result.getPersonsInfo().get(0).getAllergies());
	    }
}
