package com.safetynet.alerts.util;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

/**
 * Class performs computation of person age.
 * @author Senthil
 *
 */
@Component
public class ComputeAgeOfPerson {


    /**
     * Gets the age.
     *
     * @param birthDate the birth date
     * @return the age
     */
    public int getAge(final LocalDate birthDate) {

        LocalDate currentDate = LocalDate.now();
        int age = Period
        		.between(birthDate, currentDate)
        		.getYears();

        if (birthDate.isAfter(currentDate)) {
            throw new IllegalArgumentException("Invalid:"
            		+ " birthDate is in the future");
        } else if (age == 0) {
            age++;
        }

        return age;
    }
}
