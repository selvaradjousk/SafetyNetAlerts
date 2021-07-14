package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.model.FireStation;

public interface IFireStationDAO {

	FireStation getStationById(Integer stationId, String address);

	List<FireStation> getStationsByStationIds(int stationId);
	
}