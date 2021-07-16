package com.safetynet.alerts.dao;

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
	   
	   public List<FireStation> getStationsByStationIds(int stationId) {
		// TODO Auto-generated method stub
		return null;
	}

	public FireStation getStationByAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	public FireStation updateStation(FireStation fireStation) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteStationByMapping(final FireStation fireStation) {
		// TODO Auto-generated method stub
		
	}
}