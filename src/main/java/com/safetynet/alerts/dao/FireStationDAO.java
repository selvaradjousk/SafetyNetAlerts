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

@Repository
public class FireStationDAO implements IFireStationDAO {

	private final Map<String, FireStation> fireStationMap = new HashMap<>();
	
	
	   @Autowired
	    public FireStationDAO(final DataFileReader jsonDataArrayList) {
	        jsonDataArrayList.getFireStationList()
	        .forEach(fireStation -> fireStationMap
	        		.put(fireStation.getStationId()
	        		 + fireStation.getAddress(), fireStation));
	    }

		public FireStation getStationById(final Integer stationId, final String address) {
	        return fireStationMap.get(stationId + address);
	    }
	   
		public FireStation getStationByAddress(final String address) {
	        Collection<FireStation> fireStations = fireStationMap.values();

	        for (FireStation fireStation : fireStations) {
	            if (fireStation.getAddress().equals(address)) {
	                return fireStation;
	            }
	        }

	        return null;
	    }

		
		public List<FireStation> getStationsByStationIds(final int stationId) {
	        Collection<FireStation> fireStations = fireStationMap.values();
	        List<FireStation> fireStationsByStation = new ArrayList<>();

	        for (FireStation fireStation : fireStations) {
	            if (fireStation.getStationId() == stationId) {
	                fireStationsByStation.add(fireStation);
	            }
	        }

	        return fireStationsByStation;
	    }
		   
		   
		public FireStation updateStation(final FireStation fireStation) {
	        fireStationMap.put(fireStation.getStationId() + fireStation.getAddress(), fireStation);
	        return fireStationMap.get(fireStation.getStationId()+ fireStation.getAddress());
	    }

		public void deleteStationByMapping(final FireStation fireStation) {
	        fireStationMap.remove(fireStation.getStationId() + fireStation.getAddress());
	    }
}