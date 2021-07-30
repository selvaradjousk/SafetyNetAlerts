package com.safetynet.alerts.model;


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
 * Person DO.
 * @author Senthil
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Person {

/**
 * person first name.
 */
	@Length(min = 1, message = "Length cannot be zero")
	@NotNull(message = "must not be null")
	@NotEmpty
	private String firstName;

/**
 * person last name.
 */
	@Length(min = 1)
	@NotNull
	@NotEmpty
	private String lastName;

/**
 * person address.
 */
	private String address;

/**
 * person city.
 */
	private String city;

/**
 * person zip.
 */
	private int zip;

/**
 * person phone.
 */
	private String phone;

/**
 * person email.
 */
	private String email;
}
