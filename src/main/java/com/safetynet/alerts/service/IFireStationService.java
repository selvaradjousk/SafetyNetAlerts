package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.model.FireStation;

public interface IFireStationService {

	FireStationDTO getFireStationById(Integer stationId, String address);
	
	FireStation getFireStationByAddress(String address);
	
	List<String> getAddressesByStation(int stationId);
	
	FireStationDTO addNewFireStation(FireStationDTO newFireStation);
	
	FireStationDTO updateExistingStation(FireStationDTO existingFireStation);
	
	void deleteExistingStation(Integer stationId, String address);

	
}