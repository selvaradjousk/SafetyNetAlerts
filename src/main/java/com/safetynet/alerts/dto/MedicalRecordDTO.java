package com.safetynet.alerts.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Medical record DTO.
 * @author Senthil
 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDTO {

    /**
     * first name.
     */
	@Length(min = 1)
	@NotNull
	@NotEmpty
    private String firstName;

    /**
     * last name.
     */
	@Length(min = 1)
	@NotNull
	@NotEmpty
    private String lastName;

    /**
     * birth date.
     */
    private String birthDate;

    /**
     * medications list.
     */
    private List<String> medications;

    /**
     * allergies list.
     */
    private List<String> allergies;
}
