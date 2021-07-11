package com.safetynet.alerts.model;

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
	private String firstName;

/**
 * person last name.
 */
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
