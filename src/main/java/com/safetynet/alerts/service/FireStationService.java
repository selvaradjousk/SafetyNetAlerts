package com.safetynet.alerts.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.IFireStationDAO;
import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.exception.DataAlreadyRegisteredException;
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

    public FireStationDTO getFireStationById(
    		final Integer stationId,
    		final String address) {
    	
        FireStation fireStation = fireStationDAO
        		.getStationById(stationId, address);

        if (fireStation == null) {
            throw new DataNotFoundException(
            		"Failed to get the fireStations mapped"
            		+ " to address : " + address);
        }

        return fireStationMapper.toFireStationDTO(fireStation);
    }
    

    public FireStation getFireStationByAddress(final String address) {
        FireStation fireStation = fireStationDAO
        		.getStationByAddress(address);

        if (fireStation == null) {
            throw new DataNotFoundException(
            		"Failed to get the fireStations mapped"
            		+ " to address : " + address);
        }

        return fireStation;
    }


    public List<String> getAddressesByStation(final int station) {
        List<FireStation> fireStations = fireStationDAO
        		.getStationsByStationIds(station);
        List<String> addresses = new ArrayList<>();

        if (fireStations.isEmpty()) {
            throw new DataNotFoundException("Failed to get"
            		+ " the addresses mapped to station : " + station);
        }

        for (FireStation fireStation : fireStations) {
            addresses.add(fireStation.getAddress());
        }

        return addresses;
    }

    public FireStationDTO addNewFireStation(
    		final FireStationDTO fireStationDTO) {
    	
        FireStation fireFound = fireStationDAO
        		.getStationById(
        				fireStationDTO.getStationId(),
        				fireStationDTO.getAddress());

        if (fireFound != null) {
            throw new DataAlreadyRegisteredException(
            		"FireStation already registered");
        }
        FireStation fireToSave = fireStationMapper
        		.toFireStation(fireStationDTO);
        FireStation fireSaved = fireStationDAO
        		.updateStation(fireToSave);

        return fireStationMapper.toFireStationDTO(fireSaved);
    }
	
    public FireStationDTO updateExistingStation(
    		final FireStationDTO fireStationDTO) {
    	
        FireStation fireToUpdate = fireStationDAO
        		.getStationByAddress(fireStationDTO.getAddress());

        if (fireToUpdate == null) {
            throw new DataNotFoundException("FireStation not found");
        }

        fireToUpdate.setStationId(fireStationDTO
        		.getStationId());

        return fireStationMapper.toFireStationDTO(fireToUpdate);
    }
	

    public void deleteExistingStation(final Integer stationId, final String address) {
        FireStation fireStationToDelete = fireStationDAO.getStationById(stationId, address);

        if (fireStationToDelete == null) {
            throw new DataNotFoundException("FireStation not found");
        }

        fireStationDAO.deleteStationByMapping(fireStationToDelete);
    }

}