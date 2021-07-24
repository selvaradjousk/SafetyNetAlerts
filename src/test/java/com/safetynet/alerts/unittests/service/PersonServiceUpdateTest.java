package com.safetynet.alerts.unittests.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
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


@DisplayName("PERSON SERVICE UPDATE - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class PersonServiceUpdateTest {

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

    @DisplayName("Test UPDATE Existing PERSON")
    @Nested
    class TestUpdateNewPerson {  
    	
        @BeforeEach
        public void init() {
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

            when(personDaoMock
            		.getPersonByName(anyString(), anyString()))
            .thenReturn(testPerson1);
     
            when(personMapper
            		.toPersonDTO(any(Person.class)))
            .thenReturn(personDTO);
        }

        
        @Test
        @DisplayName("Check <Execution Order>"
        		+ " - Given a existing Person to update,"
        		+ " when Update Person action request,"
        		+ " then all steps are executed in correct order and number of expected times")
        public void testUpdatePersonExecutionOrderCheck() {

        	personService.updateExistingPerson(personDTO);

            InOrder inOrder = inOrder(personDaoMock, personMapper);
            inOrder.verify(personDaoMock).getPersonByName(anyString(), anyString());
            inOrder.verify(personMapper).toPersonDTO(any(Person.class));
        }
        
        @Test
        @DisplayName("Check <NotNull>"
        		+ " - Given a existing Person to update,"
        		+ " when Update Person action request,"
        		+ " then Person Not null")
        public void testUpdatePersonNotNullCheck() {

            PersonDTO personUpdated = personService
            		.updateExistingPerson(personDTO);

            assertNotNull(personUpdated);
            assertNotNull(personDTO);
        }
        
        @Test
        @DisplayName("Check <Validate> match of both same record instance "
        		+ " - Given a existing Person to update,"
        		+ " when Update Person action request,"
        		+ " then Person should be same as test record")
        public void testUpdatePersonDoneCheck() {

            PersonDTO personUpdated = personService
            		.updateExistingPerson(personDTO);

            assertThat(personUpdated).usingRecursiveComparison().isEqualTo(personDTO);
        }
  }
    
    @Test
    @DisplayName("UPDATE PERSON"
    		+ " - Given a non existing Person to update,"
    		+ " when Update Person action request,"
    		+ " then Person entry should display Data Not Found Exception")
    public void testUpdatePersonForNonExistingPerson() {
        when(personDaoMock
        		.getPersonByName(anyString(), anyString()))
        .thenReturn(null);

        assertThrows(DataNotFoundException.class, ()
        		-> personService.updateExistingPerson(personDTO));
    }
} 

