package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Person info DO.
 * @author Senthil
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class PersonInfo {

    /**
     * lastname.
     */
    private String lastName;

    /**
     * address.
     */
    private String address;

    /**
     * age.
     */
    private int age;

    /**
     * email.
     */
    private String email;

    /**
     * medications list.
     */
    private List<String> medications;

    /**
     * allergies list.
     */
    private List<String> allergies;
}