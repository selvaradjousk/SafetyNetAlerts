package com.safetynet.alerts.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.util.DataFileReader;

/**
 * Fire station DAO class.
 * @author Senthil
 *
 */
@Repository
public class FireStationDAO implements IFireStationDAO {

	/**
	 * fire station map.
	 */
	private final Map<String, FireStation> fireStationMap
	= new HashMap<>();

	/**
	 * Fire station DAO constructor.
	 * @param jsonDataArrayList
	 */
	@Autowired
	    public FireStationDAO(
	    		final DataFileReader jsonDataArrayList) {
	        jsonDataArrayList.getFireStationList()
	        .forEach(fireStation -> fireStationMap
	        		.put(fireStation.getStationId()
	        		 + fireStation.getAddress(), fireStation));
	    }

		/**
		 * gets station by id.
		 * @param stationId
		 * @param address
		 * @return fireStationMap
		 */
		public FireStation getStationById(
				final Integer stationId,
				final String address) {
	        return fireStationMap.get(stationId + address);
	    }

		/**
		 * gets station by address.
		 * @param address
		 * @return fireStation
		 */
		public FireStation getStationByAddress(
				final String address) {

	        Collection<FireStation> fireStations
	        = fireStationMap.values();

	        for (FireStation fireStation : fireStations) {
	            if (fireStation.getAddress().equals(address)) {
	                return fireStation;
	            }
	        }

	        return null;
	    }

		/**
		 * gets stations by station ids.
		 * @param stationId
		 * @return fireStationsByStation
		 */
		public List<FireStation> getStationsByStationIds(
				final int stationId) {

	        Collection<FireStation> fireStations
	        = fireStationMap.values();

	        List<FireStation> fireStationsByStation = new ArrayList<>();

	        for (FireStation fireStation : fireStations) {
	            if (fireStation.getStationId() == stationId) {
	                fireStationsByStation.add(fireStation);
	            }
	        }

	        return fireStationsByStation;
	    }

		/**
		 * updates station.
		 * @param fireStation
		 * @return fireStationMap by address
		 */
		public FireStation updateStation(
				final FireStation fireStation) {
	        fireStationMap.put(
	        		fireStation.getStationId()
	        		+ fireStation.getAddress(),
	        		fireStation);
	        return fireStationMap.get(fireStation.getStationId()
	        		+ fireStation.getAddress());
	    }

		/**
		 * deletes station.
		 * @param fireStation
		 */
		public void deleteStationByMapping(
				final FireStation fireStation) {
	        fireStationMap.remove(fireStation.getStationId()
	        		+ fireStation.getAddress());
	    }
}
