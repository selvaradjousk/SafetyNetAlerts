package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationDTO;

public interface IFireStationService {

	public FireStationDTO getFireStationById(final Integer stationId, final String address);

}