package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.model.FireStation;

/**
 * Fire station DAO interface.
 * @author Senthil
 *
 */
public interface IFireStationDAO {

	/**
	 * gets station by id.
	 * @param stationId
	 * @param address
	 * @return fireStationMap
	 */
	FireStation getStationById(Integer stationId, String address);

	/**
	 * gets stations by station ids.
	 * @param stationId
	 * @return fireStationsByStation
	 */
	List<FireStation> getStationsByStationIds(int stationId);

	/**
	 * gets station by address.
	 * @param address
	 * @return fireStation
	 */
	FireStation getStationByAddress(String address);

	/**
	 * updates station.
	 * @param fireStation
	 * @return fireStationMap by address
	 */
	FireStation updateStation(FireStation fireStation);

	/**
	 * deletes station.
	 * @param fireStation
	 */
	void deleteStationByMapping(FireStation fireStation);
}
