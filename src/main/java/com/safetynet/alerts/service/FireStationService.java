package com.safetynet.alerts.service;


import java.util.ArrayList;
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

    public FireStationDTO getFireStationById(final Integer stationId, final String address) {
        FireStation fireStation = fireStationDAO.getStationById(stationId, address);

        if (fireStation == null) {
            throw new DataNotFoundException("Failed to get the fireStations mapped to address : " + address);
        }

        return fireStationMapper.toFireStationDTO(fireStation);
    }
    

    public FireStation getFireStationByAddress(final String address) {
        FireStation fireStation = fireStationDAO.getStationByAddress(address);

        if (fireStation == null) {
            throw new DataNotFoundException("Failed to get the fireStations mapped to address : " + address);
        }

        return fireStation;
    }


    public List<String> getAddressesByStation(final int station) {
        List<FireStation> fireStations = fireStationDAO.getStationsByStationIds(station);
        List<String> addresses = new ArrayList<>();

        if (fireStations.isEmpty()) {
            throw new DataNotFoundException("Failed to get the addresses mapped to station : " + station);
        }

        for (FireStation fireStation : fireStations) {
            addresses.add(fireStation.getAddress());
        }

        return addresses;
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

	public void deleteExistingStation(Integer stationId, String address) {
		// TODO Auto-generated method stub
		
	}

}