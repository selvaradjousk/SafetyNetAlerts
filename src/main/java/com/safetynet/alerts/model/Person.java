package com.safetynet.alerts.model;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
	@NotBlank
	private String firstName;

/**
 * person last name.
 */
	@NotBlank
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
