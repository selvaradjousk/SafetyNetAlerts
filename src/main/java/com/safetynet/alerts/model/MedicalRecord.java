package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Medical Record DO..
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MedicalRecord {

/**
 * person first name.
 */
    private String firstName;

/**
 * person last name.
 */
    private String lastName;

/**
 * person birth date.
 */
    private String birthDate;

/**
 * medication list.
 */
    private List<String> medications;

/**
 * allergies list.
 */
    private List<String> allergies;
}
