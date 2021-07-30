package com.safetynet.alerts.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Person Address DO.
 * @author Senthil
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class PersonAddress {

    /**
     * last name.
     */
	@Length(min=1)
	@NotNull
    private String lastName;

    /**
     * phone.
     */
    private String phone;

    /**
     * age.
     */
    private int age;

    /**
     * medications list.
     */
    private List<String> medications;

    /**
     * allergies list.
     */
    private List<String> allergies;

}
