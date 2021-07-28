package com.safetynet.alerts.unittests.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.safetynet.alerts.util.ComputeAgeOfPerson;

@DisplayName("Compute Age Of Person - Unit Tests")
class ComputeAgeOfPersonTest {

    private static final ComputeAgeOfPerson computeAgeOfPerson = new ComputeAgeOfPerson();

    @ParameterizedTest(name="#{index} - Age ={0} Response: 200 OK")
    @ValueSource(ints = {
    		1,
    		9,
    		19,
    		59,
    		99,
    		129})
    @DisplayName("CHECK VALID AGE"
    		+ " - Given age 70 years"
    		+ "when age calculation,"
    		+ "the return age")
    public void testGetAgeOldPerson(int arg) {
        LocalDate birthDate = LocalDate.now().minusYears(arg);

        int age = computeAgeOfPerson.getAge(birthDate);

        assertEquals(arg, age);
    }

    @Test
    @DisplayName("CHECK INVALID AGE"
    		+ " - Given null value"
    		+ "when age calculation,"
    		+ "the return throws IllegalArgumentException")
    public void testAgeCalculationFoNullValue() {
        LocalDate birthDate = null;

        assertThrows(NullPointerException.class, ()
        		-> computeAgeOfPerson.getAge(birthDate));
    }
    
    @ParameterizedTest(name="#{index} - Age ={0} Response: 200 OK")
    @ValueSource(ints = {
    		1,
    		3,
    		7,
    		9,
    		11})
    @DisplayName("CHECK VALID AGE < 1 yr"
    		+ " - Given age in months below one year"
    		+ "when age calculation,"
    		+ "the return age as 1")
    public void testGetAgeChild(int arg) {
        LocalDate birthDate = LocalDate.now().minusMonths(arg);

        int age = computeAgeOfPerson.getAge(birthDate);

        assertEquals(1, age);
    }

    @Test
    @DisplayName("CHECK INVALID AGE"
    		+ " - Given age with future date"
    		+ "when age calculation,"
    		+ "the return throws IllegalArgumentException")
    public void testGetAgeInvalidFutureValue() {
        int age = 10;
        LocalDate birthDate = LocalDate.now().plusYears(age);

        assertThrows(IllegalArgumentException.class, ()
        		-> computeAgeOfPerson.getAge(birthDate));
    }

    @ParameterizedTest(name="#{index} - Age ={0} Response: 200 OK")
    @ValueSource(ints = {
    		-1,
    		-3,
    		-37,
    		-99,
    		-999})
    @DisplayName("CHECK INVALID AGE"
    		+ " - Given age with negative date value"
    		+ "when age calculation,"
    		+ "the return throws IllegalArgumentException")
    public void testAgeCalulationForNegativeDateValue(int arg) {
        LocalDate birthDate = LocalDate.now().minusYears(arg);

        assertThrows(IllegalArgumentException.class, ()
        		-> computeAgeOfPerson.getAge(birthDate));
    }

}
