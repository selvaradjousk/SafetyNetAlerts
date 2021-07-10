package com.safetynet.alerts.unittests.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.IPersonDAO;
import com.safetynet.alerts.dao.PersonDAO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.DataFileReader;

@DisplayName("Person DAO - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class PersonDAOTest {

    private IPersonDAO personDAO;
    
    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private DataFileReader dataFileReader;

    private static Person person1;
    private static Person person2;
    private static Person person3;
    private static Person person4;
    private static Person person5;

    @BeforeEach
    public void setUp() {
    	
        objectMapper = new ObjectMapper();
        person1 = Person.builder()
        		.firstName("Test1 FirstName")
        		.lastName("Test1 Last Name")
        		.address("Test1 Address")
        		.city("Test1 City")
        		.zip(11111)
        		.phone("111-111-1111")
        		.email("test1email@email.com")
        		.build();
        
        person2 = Person.builder()
        		.firstName("Test2 FirstName")
        		.lastName("Test2 Last Name")
        		.address("Test2 Address")
        		.city("Test2 City")
        		.zip(22222)
        		.phone("222-222-222")
        		.email("test2email@email.com")
        		.build();
        
        person3 = Person.builder()
        		.firstName("Test3 FirstName")
        		.lastName("Test3 Last Name")
        		.address("Test3 Address")
        		.city("Test3 City")
        		.zip(33333)
        		.phone("333-333-333")
        		.email("test3email@email.com")
        		.build();
        
        person4 = Person.builder()
        		.firstName("Test4 FirstName")
        		.lastName("Test4 Last Name")
        		.address("Test4 Address")
        		.city("Test4 City")
        		.zip(44444)
        		.phone("444-444-444")
        		.email("test4email@email.com")
        		.build();
        
        person5 = Person.builder()
        		.firstName("Test5 FirstName")
        		.lastName("Test5 Last Name")
        		.address("Test5 Address")
        		.city("Test5 City")
        		.zip(55555)
        		.phone("555-555-555")
        		.email("test5email@email.com")
        		.build();
    	

        when(dataFileReader
        		.getPersonList())
        .thenReturn(Arrays.asList(person1, person2, person3, person4, person5));

        personDAO = new PersonDAO(dataFileReader);
    }

    @Test
    @DisplayName("GET PERSONLIST"
    		+ " - Given a list of persons,"
    		+ " when getPersonList,"
    		+ " then return expected Person list")
    public void testGetPersonList() {
        List<Person> personList = personDAO
        		.getPersonList();

        assertEquals((Arrays.asList(person1, person2, person3, person4, person5)).size(), (personList).size());
        assertTrue((Arrays.asList(person1, person2, person3, person4, person5)).containsAll(personList));
    }
    
    @Test
    @DisplayName("GET PERSON BY IDENTITY"
    		+ " - Given a Person Id,"
    		+ " when findByIdentity action requested,"
    		+ " then return expected Person")
    public void testGetPersonByIdentity() {
        Person personFound = personDAO
        		.getPersonByName("Test1 FirstName", "Test1 Last Name");

        assertEquals(person1, personFound);
    }

    
    
    
    @Test
    @DisplayName("GET PERSON BY IDENTITY"
    		+ " - Given a Invalid Person Id,"
    		+ " when findByIdentity action requested,"
    		+ " then return null")
    public void testGetByIdentityForInvalidId() {
        Person personFound = personDAO
        		.getPersonByName("Test10 FirstName", "Test10 LastName");

        assertNull(personFound);
    }
    
    
    @Test
    @DisplayName("SAVE PERSON"
    		+ " - Given a Person entry value,"
    		+ " when SAVE action request,"
    		+ " then Person entry should be saved")
    public void testSavePerson() {
    	objectMapper = new ObjectMapper();
        person3 = Person.builder()
        		.firstName("Test3 FirstName")
        		.lastName("Test3 Last Name")
        		.address("Test3 Address")
        		.city("Test3 City")
        		.zip(33333)
        		.phone("333-333-333")
        		.email("test3email@email.com")
        		.build();

        Person personSaved = personDAO
        		.savePerson(person3);

        assertEquals(person3, personSaved);
        assertEquals(person3.getAddress(), personSaved.getAddress());
        assertEquals(person3.getEmail(), personSaved.getEmail());
    }
    
}