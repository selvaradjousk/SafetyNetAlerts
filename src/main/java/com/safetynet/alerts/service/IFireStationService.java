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
     * Gets firestation by Id.
     *
     * @param stationId the station id
     * @param address the address
     * @return fireStationMapper.toFireStationDTO(fireStation)
     */
	FireStationDTO getFireStationById(
			Integer stationId, String address);

    /**
     * Gets firestation by addresses.
     *
     * @param address the address
     * @return fireStation
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
     * adds new firestation.
     *
     * @param newFireStation the new fire station
     * @return fireStationMapper.toFireStationDTO(fireSaved)
     */
	FireStationDTO addNewFireStation(
			FireStationDTO newFireStation);

    /**
     * updates existing firestation.
     *
     * @param existingFireStation the existing fire station
     * @return fireStationMapper.toFireStationDTO(fireToUpdate)
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
