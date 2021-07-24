package com.safetynet.alerts.unittests.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.PersonDAO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.PersonMapper;


@DisplayName("PERSON SERVICE DELETE - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class PersonServiceDeleteTest {

    @Mock
    private PersonDAO personDaoMock;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    private static Person testPerson1;
    private static Person testPerson2;
    private static Person testPerson3;
    private static Person testPerson4;
    private static Person testPerson5;

    @Mock
    private static PersonDTO personDTO;

    private static List<Person> personList;
    
    private static ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        
        personDTO = personDTO.builder()
        		.firstName("Test1 FirstName")
        		.lastName("Test1 Last Name")
        		.address("Test1 Address")
        		.city("Test1 City")
        		.zip(11111)
        		.phone("111-111-1111")
        		.email("test1email@email.com")
        		.build();
        
        testPerson1 = Person.builder()
        		.firstName("Test1 FirstName")
        		.lastName("Test1 Last Name")
        		.address("Test1 Address")
        		.city("Test1 City")
        		.zip(11111)
        		.phone("111-111-1111")
        		.email("test1email@email.com")
        		.build();
        
        testPerson2 = Person.builder()
        		.firstName("Test2 FirstName")
        		.lastName("Test2 Last Name")
        		.address("Test2 Address")
        		.city("Test2 City")
        		.zip(22222)
        		.phone("222-222-222")
        		.email("test2email@email.com")
        		.build();
        
        testPerson3 = Person.builder()
        		.firstName("Test3 FirstName")
        		.lastName("Test3 Last Name")
        		.address("Test3 Address")
        		.city("Test3 City")
        		.zip(33333)
        		.phone("333-333-333")
        		.email("test3email@email.com")
        		.build();
        
        testPerson4 = Person.builder()
        		.firstName("Test4 FirstName")
        		.lastName("Test4 Last Name")
        		.address("Test4 Address")
        		.city("Test4 City")
        		.zip(44444)
        		.phone("444-444-444")
        		.email("test4email@email.com")
        		.build();
        
        testPerson5 = Person.builder()
        		.firstName("Test5 FirstName")
        		.lastName("Test5 Last Name")
        		.address("Test5 Address")
        		.city("Test5 City")
        		.zip(55555)
        		.phone("555-555-555")
        		.email("test5email@email.com")
        		.build();
        
        
        personList = Arrays.asList(testPerson1, testPerson2, testPerson3, testPerson4, testPerson5);
    }


    // ***********************************************************************************

    @DisplayName("Test DELETE Existing PERSON")
    @Nested
    class TestDeletePerson {  
    	
        @BeforeEach
        public void init() {
            when(personDaoMock
            		.getPersonByName(anyString(), anyString()))
            .thenReturn(testPerson1);
        }

    @Test
    @DisplayName("Check <NotNull>"
    		+ " - Given a Person entry value,"
    		+ " when DELETE action requested,"
    		+ " then Person entry should be deleted"
    		+ " and should not be null and should exist")
    public void testDeletePersonExistAlready() {

        assertNotNull(testPerson1);
        personService.deleteExistingPerson(testPerson1.getFirstName(), testPerson1.getLastName());
    }
    
 
    @Test
    @DisplayName("Check <Deletion Verify>"
    		+ " - Given a Person entry value,"
    		+ " when DELETE action requested,"
    		+ " then personDelete is executed corretly"
    		+ " and should confirm executing deletePerson Method")
    public void testDeletePersonVerify() {

        personService.deleteExistingPerson(testPerson1.getFirstName(), testPerson1.getLastName());
        
        verify(personDaoMock, times(1)).deletePerson(any(Person.class));
    }

    
    @Test
    @DisplayName("Check <Execution Order>"
    		+ " - Given a Person entry value,"
    		+ " when DELETE action requested,"
    		+ " then Person entry should be deleted"
    		+ " then should abid to the sequence order of actions")
    public void testDeletePersonExecutionOrderCheck() {

        personService.deleteExistingPerson(testPerson1.getFirstName(), testPerson1.getLastName());
        
        InOrder inOrder = inOrder(personDaoMock);
        inOrder.verify(personDaoMock).getPersonByName(anyString(), anyString());
        inOrder.verify(personDaoMock).deletePerson(any(Person.class));
    }
    
    }

    @Test
    @DisplayName("DELETE PERSON"
    		+ " - Given non existing Person entry value,"
    		+ " when DELETE action requested,"
    		+ " then Person entry should display Data Not Found Exception")
    public void testDeletePersonForNonExistingPerson() {
        when(personDaoMock
        		.getPersonByName(anyString(), anyString()))
        .thenReturn(null);

        assertThrows(DataNotFoundException.class, ()
        		-> personService
        		.deleteExistingPerson(testPerson1.getFirstName(), testPerson1.getLastName()));
    }
    
} 

