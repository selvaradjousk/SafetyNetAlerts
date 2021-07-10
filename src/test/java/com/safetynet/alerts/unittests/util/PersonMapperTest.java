package com.safetynet.alerts.unittests.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.PersonMapper;


@DisplayName("Person Mapper - Unit Tests")
public class PersonMapperTest {

    private PersonMapper personMapper = new PersonMapper();
    
    private static PersonDTO personDTO;
    
    private static ObjectMapper objectMapper;
    
    private static Person person;
    
    @DisplayName("Person Mapper - Unit Test")
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
        
        person = Person.builder()
        		.firstName("Test1 FirstName")
        		.lastName("Test1 Last Name")
        		.address("Test1 Address")
        		.city("Test1 City")
        		.zip(11111)
        		.phone("111-111-1111")
        		.email("test1email@email.com")
        		.build();
        
        
    }
    
    @Test
    @DisplayName("Valid Person - DTO to DO"
    		+ " - Given a PersonDTO,"
    		+ " when ToPerson,"
    		+ " then result expected Person")
    public void testPersonDtoToPersonWithValidPersonDTO() {

        Person result = personMapper
        		.toPerson(personDTO);

        assertThat(result).usingRecursiveComparison().isEqualTo(person);
    }

    @Test
    @DisplayName("Invalid Valid Person - DTO to DO"
    		+ " - Given an null PersonDTO,"
    		+ " then toPerson,"
    		+ " then should throw an NullPointerException")
    public void testPersonDtoToPersonWithInvalidPersonDTO() {
    	assertThrows(NullPointerException.class, ()
    			-> personMapper.toPerson(null));
    }
    
    @Test
    @DisplayName("Valid Person - DO to DTO"
    		+ " - Given a Person,"
    		+ " when ToPersonDTO,"
    		+ " then result expected PersonDTO")
    public void testDOToPersonDTOWithWithValidPersonValue() {

        PersonDTO result = personMapper.toPersonDTO(person);

        assertThat(result).usingRecursiveComparison().isEqualTo(personDTO);
    }

    @Test
    @DisplayName("Invalid Valid Person - DO to DTO"
    		+ " - Given an null Person,"
    		+ " then toPersonDTO,"
    		+ " then should throw an NullPointerException")
    public void testDOToPersonDTOWithNullValueException() {
    	assertThrows(NullPointerException.class, ()
    			-> personMapper.toPersonDTO(null));
    }
}

