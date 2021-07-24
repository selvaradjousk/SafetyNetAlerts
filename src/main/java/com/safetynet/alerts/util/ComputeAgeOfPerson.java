package com.safetynet.alerts.util;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

@Component
public class ComputeAgeOfPerson {


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
