package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationDTO;

public interface IFireStationService {

	public FireStationDTO getFireStationById(final String address, final Integer stationId);
	
	public FireStationDTO addNewFireStation(FireStationDTO fireStationDTO);
	
	public FireStationDTO updateExistingStation(FireStationDTO fireStationDTO);
	
	public void deleteExistingStation(String address, Integer stationId);

}