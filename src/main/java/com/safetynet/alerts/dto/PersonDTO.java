package com.safetynet.alerts.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
	@Length(min=1, message = "Length cannot be zero")
	@NotNull (message = "must not be null")
	private String firstName;

	/**
     * Person last name.
     */
	@Length(min=1)
	@NotNull
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
