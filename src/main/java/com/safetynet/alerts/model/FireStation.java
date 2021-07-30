package com.safetynet.alerts.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Fire station DO.
 * @author Senthil
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FireStation {

	/**
	 * fire station Id.
	 */
	@NotNull
	@Min(1)
	private int stationId;

	/**
	 * fire station address.
	 */
	@Length(min = 1)
	@NotNull
	private String address;
}
