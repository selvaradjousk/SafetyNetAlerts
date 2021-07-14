package com.safetynet.alerts.util;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.model.FireStation;

@Component
public class FireStationMapper {

	
    public FireStationDTO toFireStationDTO(final FireStation fireStation) {

        return new FireStationDTO(fireStation.getStationId(), fireStation.getAddress()
        		);
    }
    
    public FireStation toFireStation(final FireStationDTO fireStationDTO) {

        return new FireStation(fireStationDTO.getAddress(),
        		fireStationDTO.getStationId());
    }
}

