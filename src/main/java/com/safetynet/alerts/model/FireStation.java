package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Fire station DO.
 * 
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
	private int stationId;

	/**
	 * fire station address.
	 */
	private String address;
}
