package com.safetynet.alerts.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FireStation DTO.
 *
 * @author Senthil
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FireStationDTO {

    /**
     * FireStation Id.
     */
	@NotNull
	@Min(1)
    private int stationId;

    /**
     * FireStation Address.
     */
	@Length(min = 1)
	@NotNull
    private String address;
}
