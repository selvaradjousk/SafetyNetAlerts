package com.safetynet.alerts.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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
	@Length(min=1)
	@NotNull
	@NotEmpty
    private String firstName;

/**
 * person last name.
 */
	@Length(min=1)
	@NotNull
	@NotEmpty
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
