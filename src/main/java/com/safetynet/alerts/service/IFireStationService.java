package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.model.FireStation;

/**
 * Firestation service interface.
 * @author Senthil
 *
 */
public interface IFireStationService {

	/**
	 * Gets the fire station by id.
	 *
	 * @param stationId the station id
	 * @param address the address
	 * @return the fire station by id
	 */
	FireStationDTO getFireStationById(
			Integer stationId, String address);

	/**
	 * Gets the fire station by address.
	 *
	 * @param address the address
	 * @return the fire station by address
	 */
	FireStation getFireStationByAddress(
			String address);

    /**
     * Gets addresses by firestation Id.
     *
     * @param stationId the station id
     * @return addresses
     */
	List<String> getAddressesByStation(
			int stationId);

	/**
	 * Adds the new fire station.
	 *
	 * @param newFireStation the new fire station
	 * @return the fire station DTO
	 */
	FireStationDTO addNewFireStation(
			FireStationDTO newFireStation);

	/**
	 * Update existing station.
	 *
	 * @param existingFireStation the existing fire station
	 * @return the fire station DTO
	 */
	FireStationDTO updateExistingStation(
			FireStationDTO existingFireStation);

    /**
     * Deletes existing firestation.
     *
     * @param stationId the station id
     * @param address the address
     */
	void deleteExistingStation(
			Integer stationId, String address);
}
