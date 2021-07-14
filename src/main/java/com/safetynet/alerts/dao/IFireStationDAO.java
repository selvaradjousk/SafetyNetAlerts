package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.model.FireStation;

public interface IFireStationDAO {

	FireStation getStationById(String address, Integer stationId);

	List<FireStation> getStationsByStationIds(int stationId);
	
}