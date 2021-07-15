package com.safetynet.alerts.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.FireStation;

@Repository
public class FireStationDAO implements IFireStationDAO {


	public List<FireStation> getStationsByStationIds(int stationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FireStation getStationById(Integer stationId, String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FireStation getStationByAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}
}