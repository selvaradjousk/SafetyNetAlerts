package com.safetynet.alerts.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.IFireStationDAO;
import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.util.FireStationMapper;

@Service
public class FireStationService implements IFireStationService {


    private final IFireStationDAO fireStationDAO;
    private final FireStationMapper fireStationMapper;
    

    @Autowired
    public FireStationService(final IFireStationDAO fireStationDAO,
                             final FireStationMapper fireStationMapper) {
        this.fireStationDAO = fireStationDAO;
		this.fireStationMapper = new FireStationMapper();
    }

    public FireStationDTO getFireStationById(final String address, final Integer stationId) {
        FireStation fireStation = fireStationDAO.getStationById(address, stationId);

        if (fireStation == null) {
            throw new DataNotFoundException("Failed to get the fireStations mapped to address : " + address);
        }

        return fireStationMapper.toFireStationDTO(fireStation);
    }

	public FireStationDTO addNewFireStation(FireStationDTO fireStationDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public FireStationDTO updateExistingStation(FireStationDTO fireStationDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void deleteExistingStation(String address, Integer stationId) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public List<String> getAddressesByStation(int stationId) {
		// TODO Auto-generated method stub
		return null;
	}

}