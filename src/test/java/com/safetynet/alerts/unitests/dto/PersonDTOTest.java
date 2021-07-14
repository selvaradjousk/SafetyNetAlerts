package com.safetynet.alerts.unitests.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.PersonDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class PersonDTOTest {

	private static Validator validator;
	static PersonDTO personDTO, personDTONullCheck, personDTOLengthZero;
    private ObjectMapper objectMapper;
    
    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
		personDTO  = new PersonDTO().builder()
        		.firstName("Test First Name")
        		.lastName("Test Last Name")
        		.address("Testing Street")
        		.city("TestCity")
        		.zip(98765)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();
        
		personDTOLengthZero  = new PersonDTO().builder()
        		.firstName("")
        		.lastName("Test Last Name")
        		.address("Testing Street")
        		.city("TestCity")
        		.zip(98765)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();
		
		personDTONullCheck = new PersonDTO().builder()
        		.firstName(null)
        		.lastName("Test Last Name")
        		.address("Testing Street")
        		.city("TestCity")
        		.zip(98765)
        		.phone("111-111-1111")
        		.email("testemail@email.com")
        		.build();
		
        
    }
	
	@Test
	public void fistNameIsLengthZero() {
        Set<ConstraintViolation<PersonDTO>> constraintViolations =
                validator.validate( personDTOLengthZero );
        
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "Length cannot be zero", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void fistNameIsNull() {
        Set<ConstraintViolation<PersonDTO>> constraintViolations =
                validator.validate( personDTONullCheck );
        
        assertEquals( 1, constraintViolations.size() );
        assertEquals( "must not be null", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void fistNameIsValid() {
        Set<ConstraintViolation<PersonDTO>> constraintViolations =
                validator.validate( personDTO );

        assertEquals( 0, constraintViolations.size() );
	}

}
