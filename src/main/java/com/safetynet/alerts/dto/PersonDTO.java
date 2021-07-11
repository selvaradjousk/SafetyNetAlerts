package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Person DTO.
 * @author Senthil
 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    /**
     * Person first name.
     */
	private String firstName;

	/**
     * Person last name.
     */
	private String lastName;

    /**
     * Person address.
     */
	private String address;

    /**
     * Person city.
     */
	private String city;

    /**
     * Person zip.
     */
	private int zip;

    /**
     * Person phone.
     */
	private String phone;

    /**
     * Person email.
     */
    private String email;
}
